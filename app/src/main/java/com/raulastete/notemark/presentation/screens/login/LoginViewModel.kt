package com.raulastete.notemark.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.notemark.domain.Result
import com.raulastete.notemark.domain.repository.AuthorizationRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authorizationRepository: AuthorizationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.ClickLogin -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, isButtonEnabled = false) }

                    val result = authorizationRepository.login(
                        state.value.email,
                        state.value.password
                    )

                    _state.update { it.copy(isLoading = false, isButtonEnabled = true) }

                    when (result) {
                        is Result.Success -> {
                            eventChannel.send(LoginEvent.OnLoginSuccess)
                        }

                        is Result.Error -> {
                            eventChannel.send(LoginEvent.OnLoginFail)
                        }
                    }
                }
            }

            is LoginAction.EmailChange -> {
                _state.update {
                    it.copy(
                        email = action.email,
                        isButtonEnabled = action.email.isNotBlank() && _state.value.password.isNotBlank()
                    )
                }
            }

            is LoginAction.PasswordChange -> {
                _state.update {
                    it.copy(
                        password = action.password,
                        isButtonEnabled = _state.value.email.isNotBlank() && action.password.isNotBlank()
                    )
                }
            }

            LoginAction.TogglePasswordVisibility -> {
                _state.update {
                    it.copy(isPasswordVisible = !_state.value.isPasswordVisible)
                }
            }
        }
    }

}