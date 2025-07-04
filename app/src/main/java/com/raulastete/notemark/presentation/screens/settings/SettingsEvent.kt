package com.raulastete.notemark.presentation.screens.settings

import com.raulastete.notemark.domain.Error


sealed interface SettingsEvent {

    data object OnLogoutSuccess : SettingsEvent
    data class OnLogoutFail(val error: Error) : SettingsEvent
}