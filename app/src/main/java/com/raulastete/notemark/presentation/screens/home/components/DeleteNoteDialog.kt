package com.raulastete.notemark.presentation.screens.home.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.raulastete.notemark.R

@Composable
fun DeleteNoteDialog(
    onDelete: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = stringResource(R.string.delete_note_dialog_title),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = stringResource(R.string.delete_note_dialog_content),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text(
                    text = stringResource(R.string.delete_note_button),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = stringResource(R.string.cancel_button))
            }
        }
    )
}