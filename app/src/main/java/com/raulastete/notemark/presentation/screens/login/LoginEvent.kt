package com.raulastete.notemark.presentation.screens.login

import com.raulastete.notemark.domain.Error

sealed interface LoginEvent {

    data object OnLoginSuccess : LoginEvent
    data class OnLoginFail(val error: Error) : LoginEvent
}