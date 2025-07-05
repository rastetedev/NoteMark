package com.raulastete.notemark.presentation.screens.note_form

sealed interface NoteFormAction {

    data object ChangeToEditMode : NoteFormAction

    data object ChangeToReaderMode : NoteFormAction

    data object ClickCloseButton : NoteFormAction
    data object ClickSaveButton : NoteFormAction

    data class NoteTitleChanged(val title: String) : NoteFormAction
    data class NoteContentChanged(val content: String) : NoteFormAction

    object DiscardChanges : NoteFormAction
    object CloseDiscardChangesDialog : NoteFormAction
}