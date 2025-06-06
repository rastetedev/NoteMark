package com.raulastete.notemark.presentation.screens.registration

import com.raulastete.notemark.presentation.utils.UiText

data class RegistrationState(
    val username: String = "",
    val usernameError: UiText? = null,
    val email: String = "",
    val emailError: UiText? = null,
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val passwordError: UiText? = null,
    val passwordConfirmation: String = "",
    val isPasswordConfirmationVisible: Boolean = false,
    val passwordConfirmationError: UiText? = null,
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)