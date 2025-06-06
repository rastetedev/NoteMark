package com.raulastete.notemark.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LoginViewModel(
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginState())
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
            initialValue = LoginState()
        )

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnClickLogin -> {

            }

            is LoginAction.OnEmailChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        email = action.email,
                        isButtonEnabled = action.email.isNotBlank() && currentState.password.text.isNotBlank()
                    )
                }
            }

            is LoginAction.OnPasswordChange -> {

            }
        }
    }

}