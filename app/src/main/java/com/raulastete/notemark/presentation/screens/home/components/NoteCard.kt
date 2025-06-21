package com.raulastete.notemark.presentation.screens.home.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    noteCardUiState: NoteCardUiState,
    bodyTextLengthLimit: Int,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) {
    Card(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onLongPress = { onLongPress() },
                onTap = { onClick() }
            )
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = noteCardUiState.formattedDate,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = noteCardUiState.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(Modifier.height(4.dp))
            LimitCharactersLengthText(
                text = noteCardUiState.content,
                limit = bodyTextLengthLimit,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

data class NoteCardUiState(
    val id: String,
    val formattedDate: String,
    val title: String,
    val content: String
)