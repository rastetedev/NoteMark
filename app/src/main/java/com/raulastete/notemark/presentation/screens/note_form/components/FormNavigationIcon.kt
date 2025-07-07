package com.raulastete.notemark.presentation.screens.note_form.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.raulastete.notemark.presentation.screens.note_form.IN_ANIMATION_READER_BUTTONS
import com.raulastete.notemark.presentation.screens.note_form.NoteFormAction
import com.raulastete.notemark.presentation.screens.note_form.NoteFormUiState
import com.raulastete.notemark.presentation.screens.note_form.OUT_ANIMATION_READER_BUTTONS

@Composable
fun FormNavigationIcon(
    state: NoteFormUiState,
    navigateBack: () -> Unit,
    onAction: (NoteFormAction) -> Unit
) {
    when (state) {
        is NoteFormUiState.Edit -> {
            IconButton(onClick = {
                onAction(NoteFormAction.DiscardChanges)
            }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        is NoteFormUiState.Reader -> {
            AnimatedVisibility(
                state.showButtons,
                exit = OUT_ANIMATION_READER_BUTTONS,
                enter = IN_ANIMATION_READER_BUTTONS,
            ) {
                IconButton(onClick = {
                    onAction(NoteFormAction.ToggleReaderMode)
                }) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        else -> {
            IconButton(onClick = navigateBack) {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}