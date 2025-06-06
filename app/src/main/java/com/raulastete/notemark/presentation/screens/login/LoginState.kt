package com.raulastete.notemark.presentation.screens.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val isLoading : Boolean = false,
)