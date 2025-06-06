package com.raulastete.notemark.data.remote.dto.authentication

import com.raulastete.notemark.domain.entity.AuthInfo

fun AuthInfo.toAuthorizationResponse(): AuthorizationResponse {
    return AuthorizationResponse(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}
fun AuthorizationResponse.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}