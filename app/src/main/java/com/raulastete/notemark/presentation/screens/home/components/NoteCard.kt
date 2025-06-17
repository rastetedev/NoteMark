package com.raulastete.notemark.presentation.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.presentation.designsystem.core.NoteMarkTheme

@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    date: String,
    title: String,
    body: String,
    bodyTextLengthLimit: Int,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                date,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(Modifier.height(4.dp))
            LimitCharactersLengthText(
                text = body,
                limit = bodyTextLengthLimit,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NoteCardPreview() {
    NoteMarkTheme {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            NoteCard(
                date = "19 APR",
                title = "Title of the note",
                body = "Augue non mauris ante viverra ut arcu sed ut lectus interdum morbi sed leo purus gravida non id mi augue",
                bodyTextLengthLimit = 100
            ) { }
        }
    }
}