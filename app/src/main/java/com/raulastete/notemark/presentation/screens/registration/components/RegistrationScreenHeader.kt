package com.raulastete.notemark.presentation.screens.registration.components

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
fun RegistrationScreenHeader(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.registration_screen_title),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = textAlign,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.registration_screen_subtitle),
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = textAlign,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}