package com.raulastete.notemark.presentation.screens.registration

sealed interface RegistrationAction {
    data class OnUsernameChange(val username: String) : RegistrationAction
    data class OnEmailChange(val email: String) : RegistrationAction
    data class OnPasswordChange(val password: String) : RegistrationAction
    data class OnPasswordConfirmationChange(val passwordConfirmation: String) : RegistrationAction
    data object OnClickRegistration : RegistrationAction
}