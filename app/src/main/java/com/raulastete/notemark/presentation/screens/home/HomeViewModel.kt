package com.raulastete.notemark.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raulastete.notemark.domain.FormatNoteDateUseCase
import com.raulastete.notemark.domain.FormatUsernameInitials
import com.raulastete.notemark.domain.entity.Note
import com.raulastete.notemark.domain.repository.NoteRepository
import com.raulastete.notemark.presentation.screens.home.components.NoteCardUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class HomeViewModel(
    private val formatUsernameInitials: FormatUsernameInitials,
    private val formatNoteDateUseCase: FormatNoteDateUseCase,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _screenState = MutableStateFlow(HomeState())
    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            val usernameInitials = formatUsernameInitials()
            noteRepository.getNotes().collectLatest { noteList ->
                _screenState.update {
                    it.copy(
                        usernameInitials = usernameInitials,
                        noteList = noteList.map { note ->
                            NoteCardUiState(
                                id = note.id,
                                formattedDate = formatNoteDateUseCase(note.createdAt),
                                title = note.title,
                                content = note.content
                            )
                        }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun onAction(action: HomeAction.NoteAction) {
        when (action) {
            HomeAction.NoteAction.CreateNote -> {
                viewModelScope.launch {
                    val noteId = Clock.System.now().toEpochMilliseconds().toString()
                    withContext(Dispatchers.IO) {
                        val timestamp =
                            Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
                                .toString()
                        noteRepository.upsertNote(
                            Note(
                                id = noteId,
                                title = "Note Title",
                                content = "",
                                createdAt = timestamp,
                                updatedAt = timestamp
                            )
                        )
                    }
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
                    screenState.value.temporaryNoteDeleteId?.let {
                        withContext(Dispatchers.IO) {
                            noteRepository.deleteNote(it)
                        }
                        _screenState.update {
                            it.copy(
                                showDeleteNoteDialog = false,
                                temporaryNoteDeleteId = null
                            )
                        }
                    }
                }
            }
        }
    }
}