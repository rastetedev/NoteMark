package com.raulastete.notemark.presentation.screens.landing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.components.PrimaryButton
import com.raulastete.notemark.presentation.designsystem.components.SecondaryButton

@Composable
fun CardCredentials(
    modifier: Modifier = Modifier,
    cardCorners: CardCorners,
    paddingValues: PaddingValues = PaddingValues(16.dp),
    textAlignment: TextAlign = TextAlign.Start,
    onClickGetStarted: () -> Unit = {},
    onClickLogIn: () -> Unit = {}

) {
    Column(
        modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(
                    topStart = cardCorners.topStart,
                    topEnd = cardCorners.topEnd,
                    bottomStart = cardCorners.bottomStart,
                    bottomEnd = cardCorners.bottomEnd
                )
            )
            .padding(paddingValues)
    ) {
        Text(
            text =stringResource(R.string.landing_title),
            modifier = Modifier.fillMaxWidth(),
            textAlign = textAlignment,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.landing_subtitle),
            modifier = Modifier.fillMaxWidth(),
            textAlign = textAlignment,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(40.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.get_started_button),
            onClick = onClickGetStarted
        )

        Spacer(Modifier.height(12.dp))

        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.log_in_button),
            onClick = onClickLogIn
        )
    }

}

data class CardCorners(
    val topStart: Dp = 0.dp,
    val topEnd: Dp = 0.dp,
    val bottomStart: Dp = 0.dp,
    val bottomEnd: Dp = 0.dp,
)