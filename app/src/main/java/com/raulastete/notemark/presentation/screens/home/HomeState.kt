package com.raulastete.notemark.presentation.screens.home

import com.raulastete.notemark.presentation.screens.home.components.NoteCardUiState

data class HomeState(
    val showLoading: Boolean = false,
    val usernameInitials: String = "",
    val noteList: List<NoteCardUiState> = emptyList(),
    val showDeleteNoteDialog: Boolean = false,
    val temporaryNoteDeleteId: String? = null
)