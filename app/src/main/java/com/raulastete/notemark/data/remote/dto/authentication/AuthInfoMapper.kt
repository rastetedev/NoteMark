package com.raulastete.notemark.data.remote.dto.authentication

import com.raulastete.notemark.domain.entity.AuthInfo

fun AuthInfo.toLoginResponse(): LoginResponse {
    return LoginResponse(
        accessToken = accessToken,
        refreshToken = refreshToken,
        username = username
    )
}
fun LoginResponse.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        username = username
    )
}