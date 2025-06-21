package com.raulastete.notemark.data.remote.dto.authentication

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)