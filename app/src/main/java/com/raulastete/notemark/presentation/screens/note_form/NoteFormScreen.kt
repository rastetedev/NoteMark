package com.raulastete.notemark.presentation.screens.note_form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.core.NoteMarkTheme
import com.raulastete.notemark.presentation.screens.note_form.components.DiscardChangesDialog
import com.raulastete.notemark.presentation.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteFormRoot(
    viewModel: NoteFormViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        navigateBack()
    }

    NoteFormScreen(
        state = screenState,
        onAction = viewModel::onAction
    )

    if (screenState.showDiscardChangesDialog) {
        DiscardChangesDialog(
            onDiscard = {
                viewModel.onAction(NoteFormAction.DiscardChanges)
            },
            onKeepEditing = {
                viewModel.onAction(NoteFormAction.CloseDiscardChangesDialog)
            }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteFormScreen(
    state: NoteFormState,
    onAction: (NoteFormAction) -> Unit,
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        topBar = {

            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                title = {

                },
                navigationIcon = {
                    IconButton(onClick = {
                        onAction(NoteFormAction.ClickCloseButton)
                    }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        onAction(NoteFormAction.ClickSaveButton)
                    }) {
                        Text(
                            stringResource(R.string.save_note_button).uppercase(),
                            style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.temporaryNoteTitle,
                onValueChange = {
                    onAction(NoteFormAction.NoteTitleChanged(it))
                },
                textStyle = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                decorationBox = { innerTextField ->
                    innerTextField()
                },
                cursorBrush = SolidColor(value = MaterialTheme.colorScheme.primary)
            )

            HorizontalDivider(Modifier.height(24.dp))

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.temporaryNoteContent,
                onValueChange = {
                    onAction(NoteFormAction.NoteContentChanged(it))
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                decorationBox = { innerTextField ->
                    innerTextField()
                },
                cursorBrush = SolidColor(value = MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        NoteFormScreen(
            state = NoteFormState(),
            onAction = {}
        )
    }
}