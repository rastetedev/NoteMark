package com.raulastete.notemark.presentation.screens.note_form

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.screens.note_form.components.DiscardChangesDialog
import com.raulastete.notemark.presentation.screens.note_form.components.FormActions
import com.raulastete.notemark.presentation.screens.note_form.components.FormMetadata
import com.raulastete.notemark.presentation.screens.note_form.components.FormModeFabButton
import com.raulastete.notemark.presentation.screens.note_form.components.FormNavigationIcon
import com.raulastete.notemark.presentation.screens.note_form.components.FormTopBarTitle
import com.raulastete.notemark.presentation.utils.DeviceMode
import com.raulastete.notemark.presentation.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteFormRoot(
    deviceMode: DeviceMode,
    viewModel: NoteFormViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    fun onBack() {
        if (uiState is NoteFormUiState.Reader) {
            (context as Activity).requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        navigateBack()
    }

    fun setDeviceOrientation() {
        when {
            uiState is NoteFormUiState.Reader -> {
                (context as Activity).requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }

            else -> {
                (context as Activity).requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }

    fun requestFocusOnEditMode() {
        if (uiState is NoteFormUiState.Edit) {
            focusRequester.requestFocus()
        }
    }

    BackHandler {
        onBack()
    }

    ObserveAsEvents(viewModel.events) {
        onBack()
    }

    LaunchedEffect(uiState) {
        setDeviceOrientation()
    }

    LaunchedEffect(Unit) {
        requestFocusOnEditMode()
    }

    if (uiState is NoteFormUiState.InitialLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box {
            when (deviceMode) {
                DeviceMode.PhonePortrait, DeviceMode.TabletPortrait -> {
                    NoteFormPortraitScreen(
                        state = uiState,
                        focusRequester = focusRequester,
                        navigateBack = ::onBack,
                        onAction = viewModel::onAction
                    )
                }

                else -> {
                    NoteFormLandscapeScreen(
                        state = uiState,
                        focusRequester = focusRequester,
                        navigateBack = ::onBack,
                        onAction = viewModel::onAction
                    )
                }
            }

            if (uiState.isLoading) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if ((uiState as? NoteFormUiState.Edit)?.showDiscardChangesDialog == true) {
                DiscardChangesDialog(
                    onDiscard = {
                        viewModel.onAction(NoteFormAction.ConfirmDiscardChanges)
                    },
                    onKeepEditing = {
                        viewModel.onAction(NoteFormAction.CancelDiscardChanges)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteFormPortraitScreen(
    state: NoteFormUiState,
    focusRequester: FocusRequester,
    navigateBack: () -> Unit,
    onAction: (NoteFormAction) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                title = {
                    FormTopBarTitle(
                        state = state
                    )
                },
                navigationIcon = {
                    FormNavigationIcon(
                        state = state,
                        navigateBack = navigateBack,
                        onAction = onAction
                    )
                },
                actions = {
                    FormActions(
                        state = state,
                        onAction = onAction
                    )
                }
            )
        },
        floatingActionButton = {
            FormModeFabButton(
                noteFormUiState = state,
                onAction = onAction
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {

        NoteFormContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = state,
            focusRequester = focusRequester,
            onAction = onAction
        )
    }
}

@Composable
fun NoteFormLandscapeScreen(
    state: NoteFormUiState,
    focusRequester: FocusRequester,
    navigateBack: () -> Unit,
    onAction: (NoteFormAction) -> Unit
) {

    Scaffold(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    onAction(NoteFormAction.TouchScreen)
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        floatingActionButton = {
            FormModeFabButton(
                noteFormUiState = state,
                onAction = onAction
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Row(
                Modifier.weight(2f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FormNavigationIcon(
                    state = state,
                    navigateBack = navigateBack,
                    onAction = onAction
                )
                FormTopBarTitle(state = state)
            }

            NoteFormContent(
                modifier = Modifier.weight(6f),
                state = state,
                focusRequester = focusRequester,
                onAction = onAction
            )

            FormActions(
                modifier = Modifier.weight(2f),
                state = state,
                onAction = onAction
            )
        }
    }
}

@Composable
fun NoteFormContent(
    modifier: Modifier = Modifier,
    state: NoteFormUiState,
    focusRequester: FocusRequester? = null,
    onAction: (NoteFormAction) -> Unit
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState.value) {
        if(scrollState.isScrollInProgress){
            onAction(NoteFormAction.DismissReaderButtons)
        }
    }

    val title = when (state) {
        is NoteFormUiState.Edit -> state.temporaryTitle
        else -> state.noteData.title
    }

    val content = when (state) {
        is NoteFormUiState.Edit -> state.temporaryContent
        else -> state.noteData.content
    }

    val contentIsEditable = state is NoteFormUiState.Edit

    Column(modifier = modifier) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .then(
                    if (focusRequester != null) {
                        Modifier.focusRequester(focusRequester)
                    } else {
                        Modifier
                    }
                ),
            value = title,
            onValueChange = {
                onAction(NoteFormAction.TitleChanged(it))
            },
            enabled = contentIsEditable,
            textStyle = MaterialTheme.typography.displayMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            decorationBox = { innerTextField ->
                if (title.isEmpty()) {
                    Text(
                        stringResource(R.string.note_title_hint),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
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
                content = state.noteData.createdAtFormatted,
                modifier = Modifier.weight(1f)
            )

            FormMetadata(
                title = stringResource(R.string.date_last_edited_metadata),
                content = state.noteData.updatedAtFormatted,
                modifier = Modifier.weight(1f)
            )

        }

        HorizontalDivider()

        Box(
            Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = content,
                onValueChange = {
                    onAction(NoteFormAction.ContentChanged(it))
                },
                enabled = contentIsEditable,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                decorationBox = { innerTextField ->
                    if (content.isEmpty()) {
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
