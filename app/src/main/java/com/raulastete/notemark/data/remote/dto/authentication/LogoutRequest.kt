package com.raulastete.notemark.data.remote.dto.authentication

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequest(
    val refreshToken: String
)