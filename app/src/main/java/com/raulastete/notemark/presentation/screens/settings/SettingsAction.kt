package com.raulastete.notemark.presentation.screens.settings

sealed interface SettingsAction {

    data object Logout : SettingsAction
}