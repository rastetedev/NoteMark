package com.raulastete.notemark.presentation.screens.home

interface HomeEvent {

    data class OnNoteCreated(val noteId: String) : HomeEvent
}