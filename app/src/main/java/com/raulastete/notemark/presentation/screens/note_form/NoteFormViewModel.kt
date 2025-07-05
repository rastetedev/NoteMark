package com.raulastete.notemark.presentation.screens.note_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.raulastete.notemark.domain.Result
import com.raulastete.notemark.domain.entity.Note
import com.raulastete.notemark.domain.repository.NoteRepository
import com.raulastete.notemark.domain.usecase.FormatNoteDateInFormUseCase
import com.raulastete.notemark.presentation.navigation.Destination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class NoteFormViewModel(
    private val noteRepository: NoteRepository,
    private val formatNoteDateInFormUseCase: FormatNoteDateInFormUseCase,
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
            showLoading()
            val result = noteRepository.getNote(noteId)

            if (result is Result.Success) {
                result.data?.let { note ->
                    _screenState.update {
                        it.copy(
                            isLoading = false,
                            noteTitle = note.title,
                            noteContent = note.content,
                            noteCreated = note.createdAt,
                            noteUpdated = note.lastEditedAt,
                            formattedNoteCreated = formatNoteDateInFormUseCase(note.createdAt),
                            formattedNoteUpdated = formatNoteDateInFormUseCase(note.lastEditedAt),
                            temporaryNoteTitle = note.title,
                            temporaryNoteContent = note.content
                        )
                    }
                }
            }
        }
    }

    private fun showLoading() {
        _screenState.update {
            it.copy(isLoading = true)
        }
    }

    private fun hideLoading() {
        _screenState.update {
            it.copy(isLoading = false)
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
                } else if (screenState.value.temporaryNoteContent.isEmpty() && screenState.value.temporaryNoteTitle.isEmpty()) {
                    viewModelScope.launch {
                        showLoading()
                        noteRepository.deleteNote(noteId)
                        hideLoading()
                        eventChannel.send(NoteFormEvent.OnNoteDeleted)
                    }
                } else {
                    viewModelScope.launch {
                        eventChannel.send(NoteFormEvent.OnGoBackWithoutChanges)
                    }
                }
            }

            NoteFormAction.ClickSaveButton -> {
                viewModelScope.launch {
                    showLoading()
                    noteRepository.upsertNote(
                        Note(
                            id = noteId,
                            title = screenState.value.temporaryNoteTitle,
                            content = screenState.value.temporaryNoteContent,
                            createdAt = screenState.value.noteCreated,
                            lastEditedAt = Instant.fromEpochMilliseconds(
                                Clock.System.now().toEpochMilliseconds()
                            ).toString(),
                        )
                    )
                    hideLoading()
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
                    it.copy(showDiscardChangesDialog = false)
                }
            }

            NoteFormAction.ChangeToEditMode -> {
                _screenState.update {
                    it.copy(
                        mode = NoteFormMode.EDIT
                    )
                }
            }

            NoteFormAction.ChangeToReaderMode -> {
                _screenState.update {
                    it.copy(
                        mode = if (screenState.value.mode == NoteFormMode.READER) NoteFormMode.VIEW
                        else NoteFormMode.READER
                    )
                }
            }
        }
    }
}