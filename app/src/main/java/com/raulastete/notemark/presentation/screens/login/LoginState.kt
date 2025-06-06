package com.raulastete.notemark.presentation.screens.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val email: String = "",
    val password: TextFieldState = TextFieldState(),
    val isButtonEnabled: Boolean = false
)