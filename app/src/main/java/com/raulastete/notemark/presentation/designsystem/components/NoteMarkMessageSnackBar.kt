package com.raulastete.notemark.presentation.designsystem.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoteMarkMessageSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    isErrorMessage: Boolean = false,
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        containerColor = if(isErrorMessage) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
        contentColor = if(isErrorMessage) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimary,
        dismissActionContentColor = MaterialTheme.colorScheme.surfaceVariant
    )
}