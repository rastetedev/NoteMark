package com.raulastete.notemark.presentation.screens.home

import com.raulastete.notemark.domain.entity.Note

data class HomeState(
    val usernameInitials: String = "",
    val noteList: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val showDeleteNoteDialog: Boolean = false,
    val temporaryNoteDeleteId: String? = null
)