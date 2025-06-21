package com.raulastete.notemark.presentation.screens.note_form

interface NoteFormEvent {

    data object OnNoteChangesDiscard : NoteFormEvent
    data object OnNoteChangesSaved : NoteFormEvent
    data object OnNoteDeleted : NoteFormEvent
    object OnGoBackWithoutChanges : NoteFormEvent
}