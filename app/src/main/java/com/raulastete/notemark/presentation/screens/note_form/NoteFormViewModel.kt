package com.raulastete.notemark.presentation.screens.note_form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.raulastete.notemark.domain.entity.Note
import com.raulastete.notemark.domain.map
import com.raulastete.notemark.domain.repository.NoteRepository
import com.raulastete.notemark.domain.usecase.FormatNoteDateInFormUseCase
import com.raulastete.notemark.presentation.navigation.Destination
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class NoteFormViewModel(
    private val noteRepository: NoteRepository,
    private val formatNoteDateInFormUseCase: FormatNoteDateInFormUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<NoteFormUiState>(NoteFormUiState.InitialLoading)
    val uiState = _uiState.asStateFlow()

    private val eventChannel = Channel<NoteFormEvent>()
    val events = eventChannel.receiveAsFlow()

    var currentNoteId: String? = null
    var fadeOutButtonsInReaderModeAnimationJob: Job? = null

    init {
        savedStateHandle.toRoute<Destination.NoteForm>().noteId.let { noteId ->
            currentNoteId = noteId
            loadNote(noteId)
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun loadNote(noteId: String?) {
        viewModelScope.launch {
            _uiState.value = NoteFormUiState.InitialLoading
            if (noteId != null) {
                noteRepository.getNote(noteId).map { note ->
                    note?.let {
                        val noteData = mapNoteToNoteData(note)
                        _uiState.value =
                            NoteFormUiState.View(noteData = noteData, isLoading = false)
                    }
                }
            } else {

                val timestamp =
                    Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
                        .toString()

                _uiState.value = NoteFormUiState.Edit(
                    noteData = NoteData(
                        title = "Note Title",
                        content = "",
                        createdAtIso8601 = timestamp,
                        updatedAtIso8601 = timestamp,
                        createdAtFormatted = formatNoteDateInFormUseCase(timestamp),
                        updatedAtFormatted = formatNoteDateInFormUseCase(timestamp)
                    ),
                    isLoading = false
                )
            }
        }
    }

    private suspend fun mapNoteToNoteData(note: Note): NoteData {
        return NoteData(
            title = note.title,
            content = note.content,
            createdAtIso8601 = note.createdAt,
            updatedAtIso8601 = note.lastEditedAt,
            createdAtFormatted = formatNoteDateInFormUseCase(note.createdAt),
            updatedAtFormatted = formatNoteDateInFormUseCase(note.lastEditedAt)
        )
    }

    fun onAction(action: NoteFormAction) {
        when (action) {
            is NoteFormAction.TitleChanged -> handleTitleChanged(action.title)
            is NoteFormAction.ContentChanged -> handleContentChanged(action.content)
            NoteFormAction.ToggleEditMode -> switchToEditMode()
            NoteFormAction.ToggleReaderMode -> switchToReaderMode()
            NoteFormAction.SaveNote -> saveNote()
            NoteFormAction.DiscardChanges -> showDiscardDialog(true)
            NoteFormAction.CancelDiscardChanges -> showDiscardDialog(false)
            NoteFormAction.ConfirmDiscardChanges -> discardChangesAndSwitchToViewMode()
            NoteFormAction.TouchScreen -> toggleButtonsVisibilityInReaderMode()
            NoteFormAction.DismissReaderButtons -> dismissReaderButtons()
        }
    }

    fun handleTitleChanged(newTitle: String) {
        _uiState.update { currentState ->
            if (currentState is NoteFormUiState.Edit) {
                currentState.copy(temporaryTitle = newTitle)
            } else {
                currentState
            }
        }
    }

    private fun handleContentChanged(newContent: String) {
        _uiState.update { currentState ->
            if (currentState is NoteFormUiState.Edit) {
                currentState.copy(temporaryContent = newContent)
            } else {
                currentState
            }
        }
    }

    private fun switchToEditMode() {
        _uiState.update { currentState ->
            currentState as? NoteFormUiState.Edit
                ?: NoteFormUiState.Edit(
                    noteData = currentState.noteData,
                    isLoading = currentState.isLoading
                )
        }
    }

    private fun switchToReaderMode() {

        val currentMode = uiState.value

        if (currentMode is NoteFormUiState.Reader) {
            switchToViewMode()
        } else {
            _uiState.update { currentState ->
                currentState as? NoteFormUiState.Reader
                    ?: NoteFormUiState.Reader(
                        noteData = currentState.noteData,
                        isLoading = currentState.isLoading
                    )
            }
            startCounterToDismissButtons()
        }
    }

    private fun startCounterToDismissButtons(
        delayMillis: Long = FADE_DELAY_DURATION_IN_MILLIS
    ) {
        fadeOutButtonsInReaderModeAnimationJob = viewModelScope.launch {
            delay(delayMillis)
            _uiState.update { currentState ->
                if (currentState is NoteFormUiState.Reader) {
                    currentState.copy(showButtons = false)
                } else {
                    currentState
                }
            }
        }
    }

    private fun toggleButtonsVisibilityInReaderMode() {
        fadeOutButtonsInReaderModeAnimationJob?.cancel()
        _uiState.update { currentState ->
            if (currentState is NoteFormUiState.Reader) {
                currentState.copy(showButtons = currentState.showButtons.not())
            } else {
                currentState
            }
        }
        if (uiState.value is NoteFormUiState.Reader && (uiState.value as NoteFormUiState.Reader).showButtons) {
            startCounterToDismissButtons()
        }
    }

    private fun dismissReaderButtons() {
        fadeOutButtonsInReaderModeAnimationJob?.cancel()
        _uiState.update { currentState ->
            if (currentState is NoteFormUiState.Reader) {
                currentState.copy(showButtons = false)
            } else {
                currentState
            }
        }
    }

    private fun switchToViewMode() {
        _uiState.update { currentState ->

            if (currentState is NoteFormUiState.Edit && hasTemporaryChanges(currentState)) {
                currentState.copy(showDiscardChangesDialog = true)
            } else currentState as? NoteFormUiState.View
                ?: NoteFormUiState.View(
                    noteData = currentState.noteData,
                    isLoading = currentState.isLoading
                )
        }
    }

    private fun hasTemporaryChanges(editState: NoteFormUiState.Edit): Boolean {
        return editState.noteData.title != editState.temporaryTitle ||
                editState.noteData.content != editState.temporaryContent
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    private fun saveNote() {
        val currentEditState = _uiState.value as? NoteFormUiState.Edit ?: return

        viewModelScope.launch {
            _uiState.value = currentEditState.copy(isLoading = true)

            val noteToSave = Note(
                id = currentNoteId ?: Uuid.random().toString(),
                title = currentEditState.temporaryTitle.trim(),
                content = currentEditState.temporaryContent.trim(),
                createdAt = currentEditState.noteData.createdAtIso8601,
                lastEditedAt = Instant.fromEpochMilliseconds(
                    Clock.System.now().toEpochMilliseconds()
                ).toString(),
            )

            noteRepository.upsertNote(noteToSave)

            val updatedNoteData = mapNoteToNoteData(noteToSave)

            _uiState.value = NoteFormUiState.View(
                noteData = updatedNoteData,
                isLoading = false
            )
        }
    }

    private fun showDiscardDialog(show: Boolean) {
        _uiState.update { currentState ->
            if (currentState is NoteFormUiState.Edit) {
                if (currentState.temporaryTitle == currentState.noteData.title &&
                    currentState.temporaryContent == currentState.noteData.content
                ) {
                    NoteFormUiState.View(
                        noteData = uiState.value.noteData,
                        isLoading = false
                    )
                } else {
                    currentState.copy(showDiscardChangesDialog = show)
                }

            } else {
                currentState
            }
        }
    }

    private fun discardChangesAndSwitchToViewMode() {
        val currentEditState = _uiState.value as? NoteFormUiState.Edit ?: return

        _uiState.value = NoteFormUiState.View(
            noteData = currentEditState.noteData,
            isLoading = false
        )
    }
}