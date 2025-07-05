package com.raulastete.notemark.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.notemark.domain.usecase.FormatNoteDateInCardUseCase
import com.raulastete.notemark.domain.usecase.FormatUsernameInitialsUseCase
import com.raulastete.notemark.domain.entity.Note
import com.raulastete.notemark.domain.repository.NoteRepository
import com.raulastete.notemark.presentation.screens.home.components.NoteCardUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class HomeViewModel(
    private val formatUsernameInitialsUseCase: FormatUsernameInitialsUseCase,
    private val formatNoteDateInCardUseCase: FormatNoteDateInCardUseCase,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _screenState = MutableStateFlow(HomeState())
    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            showLoading()
            val usernameInitials = formatUsernameInitialsUseCase()
            noteRepository.getNotes().collectLatest { noteList ->
                _screenState.update {
                    it.copy(
                        showLoading = false,
                        usernameInitials = usernameInitials,
                        noteList = noteList.map { note ->
                            NoteCardUiState(
                                id = note.id,
                                formattedDate = formatNoteDateInCardUseCase(note.createdAt),
                                title = note.title,
                                content = note.content
                            )
                        }
                    )
                }
            }
        }
    }

    private fun showLoading(){
        _screenState.update {
            it.copy(showLoading = true)
        }
    }

    private fun hideLoading(){
        _screenState.update {
            it.copy(showLoading = false)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    fun onAction(action: HomeAction.NoteAction) {
        when (action) {
            HomeAction.NoteAction.CreateNote -> {
                showLoading()
                viewModelScope.launch {
                    val noteId = Uuid.random().toString()

                    val timestamp =
                        Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
                            .toString()
                    noteRepository.upsertNote(
                        Note(
                            id = noteId,
                            title = "Note Title",
                            content = "",
                            createdAt = timestamp,
                            lastEditedAt = timestamp
                        )
                    )
                    hideLoading()
                    eventChannel.send(HomeEvent.OnNoteCreated(noteId))
                }
            }

            is HomeAction.NoteAction.TryToDeleteNote -> {
                _screenState.update {
                    it.copy(
                        showDeleteNoteDialog = true,
                        temporaryNoteDeleteId = action.noteId,
                    )
                }
            }

            HomeAction.NoteAction.DismissDeleteNoteDialog -> {
                _screenState.update {
                    it.copy(
                        showDeleteNoteDialog = false,
                        temporaryNoteDeleteId = null
                    )
                }
            }

            HomeAction.NoteAction.DeleteNote -> {
                viewModelScope.launch {
                    showLoading()

                    _screenState.update {
                        it.copy(
                            showDeleteNoteDialog = false
                        )
                    }

                    screenState.value.temporaryNoteDeleteId?.let {

                        noteRepository.deleteNote(it)

                        _screenState.update {
                            it.copy(
                                showLoading = false,
                                temporaryNoteDeleteId = null
                            )
                        }
                    }
                }
            }
        }
    }
}