package com.raulastete.notemark.presentation.screens.login

sealed interface LoginAction {
    data class EmailChange(val email: String) : LoginAction
    data class PasswordChange(val password: String) : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data object ClickLogin : LoginAction
}