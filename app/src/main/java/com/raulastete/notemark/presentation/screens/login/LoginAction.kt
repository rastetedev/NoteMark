package com.raulastete.notemark.presentation.screens.login

sealed interface LoginAction {
    data class OnEmailChange(val email: String) : LoginAction
    data class OnPasswordChange(val password: String) : LoginAction
    data object OnClickLogin : LoginAction
}