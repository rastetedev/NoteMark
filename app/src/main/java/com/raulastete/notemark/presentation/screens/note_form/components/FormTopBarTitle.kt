package com.raulastete.notemark.presentation.screens.note_form.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.screens.note_form.NoteFormUiState

@Composable
fun FormTopBarTitle(state: NoteFormUiState) {
    when (state) {
        is NoteFormUiState.Edit -> {
            Text("")
        }

        else -> {
            Text(
                stringResource(R.string.all_notes),
                style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}