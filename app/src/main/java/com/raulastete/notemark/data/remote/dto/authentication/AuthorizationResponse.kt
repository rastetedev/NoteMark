package com.raulastete.notemark.data.remote.dto.authentication

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationResponse(
    val accessToken: String,
    val refreshToken: String,
    val username: String
)