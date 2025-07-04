package com.raulastete.notemark.presentation.screens.home

sealed interface HomeAction {

    sealed interface NavigationAction {

        data class OnNoteCardClick(val noteId: String) : NavigationAction
        data object OnSettingsClick : NavigationAction
    }

    sealed interface NoteAction {

        data object CreateNote : NoteAction
        data class TryToDeleteNote(val noteId: String) : NoteAction
        data object DismissDeleteNoteDialog : NoteAction
        data object DeleteNote : NoteAction
    }
}