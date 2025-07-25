package com.raulastete.notemark.data.remote.service.ktor

import com.raulastete.notemark.BuildConfig
import com.raulastete.notemark.data.remote.dto.authentication.RefreshTokenRequest
import com.raulastete.notemark.data.remote.dto.authentication.RefreshTokenResponse
import com.raulastete.notemark.domain.SessionStorage
import com.raulastete.notemark.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {

    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                header("X-User-Email", BuildConfig.EMAIL)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val info = sessionStorage.get()
                        BearerTokens(
                            accessToken = info?.accessToken ?: "",
                            refreshToken = info?.refreshToken ?: ""
                        )
                    }
                    refreshTokens {
                        val info = sessionStorage.get()
                        val response = client.post<RefreshTokenRequest, RefreshTokenResponse>(
                            route = ApiUrl.REFRESH_TOKEN,
                            body = RefreshTokenRequest(
                                refreshToken = info?.refreshToken ?: "",
                            )
                        )

                        if (response is Result.Success) {
                            val newAuthInfo = info?.copy(
                                accessToken = response.data.accessToken,
                                refreshToken = response.data.refreshToken,
                            )

                            sessionStorage.set(newAuthInfo)

                            BearerTokens(
                                accessToken = newAuthInfo?.accessToken!!,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        } else {
                            sessionStorage.set(null)

                            BearerTokens(
                                accessToken = "",
                                refreshToken = ""
                            )
                        }
                    }
                }
            }
        }
    }
}