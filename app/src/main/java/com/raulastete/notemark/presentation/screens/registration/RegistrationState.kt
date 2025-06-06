package com.raulastete.notemark.presentation.screens.registration

data class RegistrationState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val isButtonEnabled: Boolean = false
)