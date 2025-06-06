package com.raulastete.notemark.data.remote.dto.authentication

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)