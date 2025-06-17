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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.components.NoteMarkFab
import com.raulastete.notemark.presentation.designsystem.core.NoteMarkTheme
import com.raulastete.notemark.presentation.screens.home.components.NoteCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
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
                onClick = {}
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
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(state.noteList, key = { it.id }) { item ->
                        NoteCard(
                            date = item.date,
                            title = item.title,
                            body = item.content,
                            bodyTextLengthLimit = 150
                        ) { }
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
            onAction = {}
        )
    }
}