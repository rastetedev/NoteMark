package com.raulastete.notemark.data.repository

import com.raulastete.notemark.data.remote.dto.authentication.LoginRequest
import com.raulastete.notemark.data.remote.dto.authentication.LoginResponse
import com.raulastete.notemark.data.remote.dto.authentication.LogoutRequest
import com.raulastete.notemark.data.remote.dto.authentication.RegistrationRequest
import com.raulastete.notemark.data.remote.service.ktor.ApiUrl
import com.raulastete.notemark.data.remote.service.ktor.post
import com.raulastete.notemark.domain.DataError
import com.raulastete.notemark.domain.EmptyDataResult
import com.raulastete.notemark.domain.Result
import com.raulastete.notemark.domain.SessionStorage
import com.raulastete.notemark.domain.asEmptyDataResult
import com.raulastete.notemark.domain.entity.AuthInfo
import com.raulastete.notemark.domain.repository.AuthorizationRepository
import io.ktor.client.HttpClient

class DefaultAuthorizationRepository(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
) : AuthorizationRepository {

    override suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = ApiUrl.LOGIN,
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    username = result.data.username
                )
            )
        }
        return result.asEmptyDataResult()
    }

    override suspend fun registration(
        username: String,
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network> {
        return httpClient.post<RegistrationRequest, Unit>(
            route = ApiUrl.REGISTRATION,
            body = RegistrationRequest(
                username = username,
                email = email,
                password = password
            )
        )
    }

    override suspend fun logout(refreshToken: String): EmptyDataResult<DataError.Network> {
        return httpClient.post<LogoutRequest, Unit>(
            route = ApiUrl.LOGOUT,
            body = LogoutRequest(
                refreshToken = refreshToken
            )
        )
    }
}