package com.raulastete.notemark.presentation.screens.home

import com.raulastete.notemark.domain.entity.Note

data class HomeState(
    val username: String = "",
    val usernameInitials : String = "",
    val noteList: List<Note> = emptyList(),
    val isLoading: Boolean = false
)