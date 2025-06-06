package com.raulastete.notemark.presentation.screens.login

sealed interface LoginEvent {

    data object OnLoginSuccess : LoginEvent
    data object OnLoginFail : LoginEvent
}