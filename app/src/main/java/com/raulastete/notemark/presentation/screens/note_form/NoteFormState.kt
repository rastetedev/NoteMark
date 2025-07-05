package com.raulastete.notemark.presentation.screens.note_form

data class NoteFormState(
    val isLoading: Boolean = false,
    val noteTitle: String = "",
    val noteContent: String = "",
    val noteCreated: String = "",
    val noteUpdated: String = "",
    val temporaryNoteTitle: String = "",
    val temporaryNoteContent: String = "",
    val formattedNoteCreated: String = "",
    val formattedNoteUpdated: String = "",
    val showDiscardChangesDialog: Boolean = false,
    val mode: NoteFormMode = NoteFormMode.VIEW
)

enum class NoteFormMode {
    VIEW,
    EDIT,
    READER
}