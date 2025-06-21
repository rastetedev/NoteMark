package com.raulastete.notemark.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.components.NoteMarkFab
import com.raulastete.notemark.presentation.designsystem.core.NoteMarkTheme
import com.raulastete.notemark.presentation.screens.home.components.DeleteNoteDialog
import com.raulastete.notemark.presentation.screens.home.components.NoteCard
import com.raulastete.notemark.presentation.utils.DeviceMode
import com.raulastete.notemark.presentation.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun HomeRoot(
    deviceMode: DeviceMode,
    viewModel: HomeViewModel = koinViewModel(),
    navigateToNoteForm: (noteId: String) -> Unit
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is HomeEvent.OnNoteCreated -> {
                navigateToNoteForm(event.noteId)
            }
        }
    }

    val columnNumbers: Int = when (deviceMode) {
        DeviceMode.PhonePortrait, DeviceMode.TabletPortrait -> 2
        else -> 3
    }

    val maxNoteContentLength = when (deviceMode) {
        DeviceMode.PhonePortrait, DeviceMode.PhoneLandscape -> 150
        DeviceMode.TabletPortrait, DeviceMode.TabletLandscape -> 250
    }

    HomeScreen(
        state = state,
        columnNumbers = columnNumbers,
        maxNoteContentLength = maxNoteContentLength,
        onNavigate = {
            when (it) {
                is HomeAction.NavigationAction.OnNoteCardClick -> navigateToNoteForm(it.noteId)
            }
        },
        onAction = viewModel::onAction
    )

    if (state.showDeleteNoteDialog) {
        Dialog(
            onDismissRequest = { viewModel.onAction(HomeAction.NoteAction.DismissDeleteNoteDialog) }
        ) {
            DeleteNoteDialog(
                onDelete = {
                    viewModel.onAction(HomeAction.NoteAction.DeleteNote)
                },
                onCancel = {
                    viewModel.onAction(HomeAction.NoteAction.DismissDeleteNoteDialog)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    columnNumbers: Int,
    maxNoteContentLength: Int,
    onNavigate: (HomeAction.NavigationAction) -> Unit,
    onAction: (HomeAction.NoteAction) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.home_screen_title))
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .size(40.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp)),

                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.usernameInitials,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        floatingActionButton = {
            NoteMarkFab(
                onClick = {
                    onAction(HomeAction.NoteAction.CreateNote)
                }
            )
        }
    ) { innerPadding ->

        when {
            state.noteList.isEmpty() -> {
                HomeEmptyContent(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 80.dp)
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            else -> {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    columns = StaggeredGridCells.Fixed(columnNumbers),
                    contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(state.noteList, key = { it.id }) { item ->
                        NoteCard(
                            noteCardUiState = item,
                            bodyTextLengthLimit = maxNoteContentLength,
                            onClick = {
                                onNavigate(HomeAction.NavigationAction.OnNoteCardClick(item.id))
                            },
                            onLongPress = {
                                onAction(HomeAction.NoteAction.TryToDeleteNote(item.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeEmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = stringResource(R.string.empty_note_list),
            style = MaterialTheme.typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        HomeScreen(
            state = HomeState(),
            columnNumbers = 2,
            maxNoteContentLength = 150,
            onNavigate = {},
            onAction = {}
        )
    }
}