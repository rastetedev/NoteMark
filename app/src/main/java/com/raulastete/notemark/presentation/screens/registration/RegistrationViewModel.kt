package com.raulastete.notemark.presentation.screens.registration

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.notemark.R
import com.raulastete.notemark.domain.Result
import com.raulastete.notemark.domain.UserDataValidator
import com.raulastete.notemark.domain.repository.AuthorizationRepository
import com.raulastete.notemark.presentation.utils.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val authorizationRepository: AuthorizationRepository,
    private val userDataValidator: UserDataValidator,
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<RegistrationEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: RegistrationAction) {
        when (action) {
            RegistrationAction.ClickRegistration -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, isButtonEnabled = false) }

                    val result = authorizationRepository.registration(
                        state.value.username,
                        state.value.email,
                        state.value.password.text.toString()
                    )

                    _state.update { it.copy(isLoading = false, isButtonEnabled = true) }

                    when (result) {
                        is Result.Success -> {
                            eventChannel.send(RegistrationEvent.OnRegistrationSuccess)
                        }

                        is Result.Error -> {
                            eventChannel.send(RegistrationEvent.OnRegistrationFail)
                        }
                    }
                }
            }

            is RegistrationAction.UsernameChange -> {
                _state.update {
                    it.copy(
                        username = action.username,
                        usernameError = when {
                            userDataValidator.isValidUsername(action.username).greaterThanMinLength.not() ->
                                UiText.StringResource(R.string.error_username_min_length)

                            userDataValidator.isValidUsername(action.username).lowerThanMaxLength.not() ->
                                UiText.StringResource(R.string.error_username_max_length)

                            else -> null
                        }
                    )
                }
                validateButtonState()
            }

            is RegistrationAction.EmailChange -> {
                _state.update {
                    it.copy(
                        email = action.email,
                        emailError = when {
                            userDataValidator.isValidEmail(action.email).not() ->
                                UiText.StringResource(R.string.error_email_invalid)

                            else -> null
                        },
                    )
                }
                validateButtonState()
            }

            is RegistrationAction.PasswordChange -> {
                _state.update {
                    it.copy(
                        password = TextFieldState(initialText = action.password),
                        passwordError = when {
                            userDataValidator.isValidPassword(action.password).isValid ->
                                UiText.StringResource(R.string.error_password_invalid)

                            else -> null
                        }
                    )
                }
                validateButtonState()
            }

            is RegistrationAction.PasswordConfirmationChange -> {
                _state.update {
                    it.copy(
                        passwordConfirmation = TextFieldState(action.passwordConfirmation),
                        passwordConfirmationError = when {
                            action.passwordConfirmation != it.password.toString() ->
                                UiText.StringResource(R.string.error_password_confirmation_mismatch)

                            else -> null
                        }
                    )
                }
                validateButtonState()
            }
        }
    }

    private fun validateButtonState() {
        _state.update {
            it.copy(
                isButtonEnabled = it.usernameError == null &&
                        it.emailError == null &&
                        it.passwordError == null &&
                        it.passwordConfirmationError == null &&
                        it.username.isNotBlank() &&
                        it.email.isNotBlank() &&
                        it.password.text.isNotBlank() &&
                        it.passwordConfirmation.text.isNotBlank()
            )
        }
    }
}