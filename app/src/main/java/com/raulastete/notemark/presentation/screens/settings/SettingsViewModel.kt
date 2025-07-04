package com.raulastete.notemark.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.notemark.domain.Result
import com.raulastete.notemark.domain.SessionStorage
import com.raulastete.notemark.domain.repository.AuthorizationRepository
import com.raulastete.notemark.domain.repository.NoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val authorizationRepository: AuthorizationRepository,
    private val noteRepository: NoteRepository,
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val eventChannel = Channel<SettingsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.Logout -> logout()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }



            val result = authorizationRepository.logout(
                sessionStorage.get()?.refreshToken ?: ""
            )

            noteRepository.deleteAll()
            sessionStorage.set(null)

            _state.update { it.copy(isLoading = false) }

            when (result) {
                is Result.Success -> {
                    eventChannel.send(SettingsEvent.OnLogoutSuccess)
                }

                is Result.Error -> {
                    eventChannel.send(SettingsEvent.OnLogoutFail(result.error))
                }
            }
        }
    }

}