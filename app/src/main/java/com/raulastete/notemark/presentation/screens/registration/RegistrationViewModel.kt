package com.raulastete.notemark.presentation.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class RegistrationViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegistrationState()
        )

    fun onAction(action: RegistrationAction) {
        when (action) {
            RegistrationAction.OnClickRegistration -> {

            }

            is RegistrationAction.OnUsernameChange -> {
                _state.update {
                    it.copy(
                        username = action.username
                    )
                }
            }

            is RegistrationAction.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = action.email
                    )
                }
            }

            is RegistrationAction.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = action.password
                    )
                }
            }

            is RegistrationAction.OnPasswordConfirmationChange -> {
                _state.update {
                    it.copy(
                        passwordConfirmation = action.passwordConfirmation
                    )
                }
            }

        }
    }

}