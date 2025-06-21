package com.raulastete.notemark.domain.entity

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val username: String,
)