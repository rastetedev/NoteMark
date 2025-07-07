package com.raulastete.notemark.presentation.screens.note_form.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.screens.note_form.NoteFormAction
import com.raulastete.notemark.presentation.screens.note_form.NoteFormUiState

@Composable
fun FormActions(
    modifier: Modifier = Modifier,
    state: NoteFormUiState,
    onAction: (NoteFormAction) -> Unit
) {
    if (state is NoteFormUiState.Edit) {
        TextButton(
            modifier = modifier,
            onClick = {
                onAction(NoteFormAction.SaveNote)
            }) {
            Text(
                stringResource(R.string.save_note_button).uppercase(),
                style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
            )
        }
    } else {
        Box(modifier) {}
    }
}