package com.raulastete.notemark.data.preferences

import android.content.SharedPreferences
import com.raulastete.notemark.domain.entity.AuthInfo
import com.raulastete.notemark.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.core.content.edit
import com.raulastete.notemark.data.remote.dto.authentication.AuthorizationResponse
import com.raulastete.notemark.data.remote.dto.authentication.toAuthorizationResponse
import com.raulastete.notemark.data.remote.dto.authentication.toAuthInfo

class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences
): SessionStorage {

    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                Json.decodeFromString<AuthorizationResponse>(it).toAuthInfo()
            }
        }
    }
    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if(info == null) {
                sharedPreferences.edit(commit = true) { remove(KEY_AUTH_INFO) }
                return@withContext
            }
            val json = Json.encodeToString(info.toAuthorizationResponse())
            sharedPreferences
                .edit(commit = true) {
                    putString(KEY_AUTH_INFO, json)
                }
        }
    }
    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}