package com.raulastete.notemark.presentation.screens.registration

sealed interface RegistrationEvent {

    data object OnRegistrationSuccess : RegistrationEvent
    data object OnRegistrationFail : RegistrationEvent
}