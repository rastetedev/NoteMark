package com.raulastete.notemark.presentation.screens.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.R

@Composable
fun LoginScreenHeader(modifier: Modifier = Modifier, textAlignment: TextAlign = TextAlign.Start) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.login_screen_title),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = textAlignment,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.login_screen_subtitle),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = textAlignment,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}