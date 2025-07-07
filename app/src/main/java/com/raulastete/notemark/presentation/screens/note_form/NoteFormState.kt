package com.raulastete.notemark.presentation.screens.note_form

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut

data class NoteData(
    val title: String = "",
    val content: String = "",
    val createdAtIso8601: String = "",
    val updatedAtIso8601: String = "",
    val createdAtFormatted: String = "",
    val updatedAtFormatted: String = ""
)

sealed interface NoteFormUiState {
    val noteData: NoteData
    val isLoading: Boolean

    data class View(
        override val noteData: NoteData,
        override val isLoading: Boolean = false
    ) : NoteFormUiState

    data class Edit(
        override val noteData: NoteData,
        val temporaryTitle: String = noteData.title,
        val temporaryContent: String = noteData.content,
        val showDiscardChangesDialog: Boolean = false,
        override val isLoading: Boolean = false
    ) : NoteFormUiState

    data class Reader(
        override val noteData: NoteData,
        override val isLoading: Boolean = false,
        val showButtons: Boolean = true
    ) : NoteFormUiState

    data object InitialLoading : NoteFormUiState {
        override val noteData: NoteData get() = NoteData()
        override val isLoading: Boolean get() = true
    }
}

val FADE_OUT_ANIMATION_READER_BUTTONS = fadeOut(tween(durationMillis = FADE_ANIMATION_DURATION_IN_MILLIS.toInt()))
const val FADE_ANIMATION_DURATION_IN_MILLIS = 3000L