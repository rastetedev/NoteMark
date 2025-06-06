package com.raulastete.notemark.presentation.screens.registration

sealed interface RegistrationAction {
    data class UsernameChange(val username: String) : RegistrationAction
    data class EmailChange(val email: String) : RegistrationAction
    data class PasswordChange(val password: String) : RegistrationAction
    data class PasswordConfirmationChange(val passwordConfirmation: String) : RegistrationAction
    data object ClickRegistration : RegistrationAction
}