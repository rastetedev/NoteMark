package com.raulastete.notemark.presentation.screens.note_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.raulastete.notemark.domain.entity.Note
import com.raulastete.notemark.domain.repository.NoteRepository
import com.raulastete.notemark.presentation.navigation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class NoteFormViewModel(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _screenState = MutableStateFlow(NoteFormState())
    val screenState = _screenState.asStateFlow()
    var noteId = ""

    private val eventChannel = Channel<NoteFormEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        val args = savedStateHandle.toRoute<Destination.NoteForm>()
        noteId = args.noteId

        viewModelScope.launch {
            val note = withContext(Dispatchers.IO) {
                noteRepository.getNote(noteId)
            }
            note?.let {
                _screenState.update {
                    it.copy(
                        noteTitle = note.title,
                        noteContent = note.content,
                        noteCreated = note.createdAt,
                        temporaryNoteTitle = note.title,
                        temporaryNoteContent = note.content,
                    )
                }
            }
        }

    }

    @OptIn(ExperimentalTime::class)
    fun onAction(action: NoteFormAction) {
        when (action) {

            is NoteFormAction.NoteContentChanged -> {
                _screenState.update {
                    it.copy(
                        temporaryNoteContent = action.content
                    )
                }
            }

            is NoteFormAction.NoteTitleChanged -> {
                _screenState.update {
                    it.copy(
                        temporaryNoteTitle = action.title
                    )
                }
            }

            NoteFormAction.ClickCloseButton -> {
                if (screenState.value.temporaryNoteContent != screenState.value.noteContent || screenState.value.temporaryNoteTitle != screenState.value.noteTitle) {
                    _screenState.update {
                        it.copy(showDiscardChangesDialog = true)
                    }
                } else if(screenState.value.temporaryNoteContent.isEmpty()) {
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            noteRepository.deleteNote(noteId)
                            eventChannel.send(NoteFormEvent.OnNoteDeleted)
                        }
                    }
                } else {
                    viewModelScope.launch {
                        eventChannel.send(NoteFormEvent.OnGoBackWithoutChanges)
                    }
                }
            }

            NoteFormAction.ClickSaveButton -> {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        noteRepository.upsertNote(
                            Note(
                                id = noteId,
                                title = screenState.value.temporaryNoteTitle,
                                content = screenState.value.temporaryNoteContent,
                                createdAt = screenState.value.noteCreated,
                                updatedAt = Clock.System.now().toEpochMilliseconds().toString(),
                            )
                        )
                    }

                    eventChannel.send(NoteFormEvent.OnNoteChangesSaved)
                }
            }

            NoteFormAction.DiscardChanges -> {
                viewModelScope.launch {
                    eventChannel.send(NoteFormEvent.OnNoteChangesDiscard)
                }
            }

            NoteFormAction.CloseDiscardChangesDialog -> {
                _screenState.update {
                    it.copy(
                        showDiscardChangesDialog = false
                    )
                }
            }
        }
    }
}