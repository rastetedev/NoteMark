package com.raulastete.notemark.presentation.screens.note_form.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.raulastete.notemark.R

@Composable
fun DiscardChangesDialog(
    onDiscard: () -> Unit,
    onKeepEditing: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onKeepEditing,
        title = {
            Text(
                text = stringResource(R.string.discard_changes_note_dialog_title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = stringResource(R.string.discard_changes_note_dialog_content),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = onDiscard) {
                Text(
                    text = stringResource(R.string.discard_note_button),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onKeepEditing) {
                Text(text = stringResource(R.string.keep_editing_button))
            }
        }
    )
}