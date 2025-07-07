package com.raulastete.notemark.presentation.screens.note_form

sealed interface NoteFormAction {

    data object ToggleEditMode : NoteFormAction
    data object ToggleReaderMode : NoteFormAction
    data object SaveNote : NoteFormAction

    data class TitleChanged(val title: String) : NoteFormAction
    data class ContentChanged(val content: String) : NoteFormAction

    object DiscardChanges : NoteFormAction
    object ConfirmDiscardChanges : NoteFormAction
    object CancelDiscardChanges : NoteFormAction

    object TouchScreen : NoteFormAction
}