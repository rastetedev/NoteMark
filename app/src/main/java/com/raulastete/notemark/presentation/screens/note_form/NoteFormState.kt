package com.raulastete.notemark.presentation.screens.note_form

data class NoteFormState(
    val noteTitle: String = "",
    val noteContent: String = "",
    val noteCreated: String = "",
    val temporaryNoteTitle: String = "",
    val temporaryNoteContent: String = "",
    val showDiscardChangesDialog: Boolean = false
)