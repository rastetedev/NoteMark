package com.raulastete.notemark.data.remote.dto.authentication

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val username: String
)