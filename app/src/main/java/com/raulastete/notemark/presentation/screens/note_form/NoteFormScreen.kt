package com.raulastete.notemark.presentation.screens.note_form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.core.NoteMarkTheme
import com.raulastete.notemark.presentation.screens.note_form.components.DiscardChangesDialog
import com.raulastete.notemark.presentation.screens.note_form.components.FormMetadata
import com.raulastete.notemark.presentation.screens.note_form.components.FormModeFabButton
import com.raulastete.notemark.presentation.utils.DeviceMode
import com.raulastete.notemark.presentation.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteFormRoot(
    deviceMode: DeviceMode,
    viewModel: NoteFormViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    ObserveAsEvents(viewModel.events) { event ->
        navigateBack()
    }

    when (deviceMode) {
        DeviceMode.PhonePortrait, DeviceMode.TabletPortrait -> {
            NoteFormScreen(
                state = screenState,
                focusRequester = focusRequester,
                onAction = viewModel::onAction
            )
        }

        else -> {
            NoteFormLandscapeScreen(
                state = screenState,
                focusRequester = focusRequester,
                onAction = viewModel::onAction
            )
        }
    }

    if (screenState.isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }


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
    focusRequester: FocusRequester,
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
        },
        floatingActionButton = {
            FormModeFabButton(
                noteFormMode = state.mode,
                onClickEditMode = {
                    onAction(NoteFormAction.ChangeToEditMode)
                },
                onClickReaderMode = {
                    onAction(NoteFormAction.ChangeToReaderMode)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .focusRequester(focusRequester),
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

            HorizontalDivider()

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                FormMetadata(
                    title = stringResource(R.string.date_created_metadata),
                    content = state.noteCreated,
                    modifier = Modifier.weight(1f)
                )

                FormMetadata(
                    title = stringResource(R.string.date_last_edited_metadata),
                    content = state.noteUpdated,
                    modifier = Modifier.weight(1f)
                )

            }

            HorizontalDivider()

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
                    if (state.temporaryNoteContent.isEmpty()) {
                        Text(
                            stringResource(R.string.note_content_hint),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        )
                    }
                    innerTextField()
                },
                cursorBrush = SolidColor(value = MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun NoteFormLandscapeScreen(
    state: NoteFormState,
    focusRequester: FocusRequester,
    onAction: (NoteFormAction) -> Unit
) {
    Scaffold(containerColor = MaterialTheme.colorScheme.surfaceVariant) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Spacer(Modifier.weight(1f))

            IconButton(onClick = {
                onAction(NoteFormAction.ClickCloseButton)
            }) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.weight(1f))

            Column(
                Modifier
                    .weight(8f)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .focusRequester(focusRequester),
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
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
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

            Spacer(Modifier.width(24.dp))

            TextButton(onClick = {
                onAction(NoteFormAction.ClickSaveButton)
            }) {
                Text(
                    stringResource(R.string.save_note_button).uppercase(),
                    style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
                )
            }

            Spacer(Modifier.width(24.dp))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        NoteFormScreen(
            state = NoteFormState(),
            focusRequester = remember { FocusRequester() },
            onAction = {}
        )
    }
}