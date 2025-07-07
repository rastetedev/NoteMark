package com.raulastete.notemark.presentation.screens.note_form.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.screens.note_form.NoteFormAction
import com.raulastete.notemark.presentation.screens.note_form.NoteFormUiState

@Composable
fun FormModeFabButton(
    noteFormUiState: NoteFormUiState,
    onAction : (NoteFormAction) -> Unit
) {

    val backgroundEditButtonColor = animateColorAsState(
        if (noteFormUiState is NoteFormUiState.Edit) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        } else {
            MaterialTheme.colorScheme.surface
        }
    )

    val foregroundEditButtonColor = animateColorAsState(
        if (noteFormUiState is NoteFormUiState.Edit) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface
        }
    )

    val backgroundReaderButtonColor = animateColorAsState(
        if (noteFormUiState is NoteFormUiState.Reader) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        } else {
            MaterialTheme.colorScheme.surface
        }
    )

    val foregroundReaderButtonColor = animateColorAsState(
        if (noteFormUiState is NoteFormUiState.Reader) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface
        }
    )

    Row(
        Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = foregroundEditButtonColor.value
            ),
            onClick = { onAction(NoteFormAction.ToggleEditMode) },
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(12.dp),
                    color = backgroundEditButtonColor.value
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.edit_mode),
                contentDescription = null
            )
        }
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = foregroundReaderButtonColor.value
            ),
            onClick = { onAction(NoteFormAction.ToggleReaderMode) },
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(12.dp),
                    color = backgroundReaderButtonColor.value
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.reader_mode),
                contentDescription = null
            )
        }
    }
}