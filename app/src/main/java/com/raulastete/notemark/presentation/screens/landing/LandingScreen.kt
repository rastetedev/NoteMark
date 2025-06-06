package com.raulastete.notemark.presentation.screens.landing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.presentation.screens.landing.components.CardCorners
import com.raulastete.notemark.presentation.screens.landing.components.CardCredentials
import com.raulastete.notemark.presentation.screens.landing.components.LandingMainImage
import com.raulastete.notemark.presentation.utils.DeviceMode

@Composable
fun LandingRoot(
    deviceMode: DeviceMode,
    navigateToLogin: () -> Unit,
    navigateToRegistration: () -> Unit
) {

    val cardCredentialsModifier =
        when (deviceMode) {
            DeviceMode.TabletPortrait -> Modifier.padding(horizontal = 60.dp)
            else -> Modifier
        }

    val paddingValues =
        when (deviceMode) {
            DeviceMode.PhonePortrait -> PaddingValues(
                vertical = 32.dp,
                horizontal = 16.dp
            )

            DeviceMode.TabletPortrait -> PaddingValues(48.dp)

            else -> PaddingValues(
                start = 60.dp,
                end = 40.dp,
                top = 40.dp,
                bottom = 40.dp
            )
        }

    val cardCorners =
        when (deviceMode) {
            DeviceMode.TabletPortrait -> CardCorners(
                topStart = 24.dp,
                topEnd = 24.dp
            )

            DeviceMode.TabletLandscape -> CardCorners(
                topStart = 24.dp,
                bottomStart = 24.dp
            )

            else -> CardCorners(
                topStart = 20.dp,
                topEnd = 20.dp
            )
        }

    val textAlign =
        when (deviceMode) {
            DeviceMode.TabletPortrait, DeviceMode.TabletLandscape -> TextAlign.Center
            else -> TextAlign.Start
        }


    when (deviceMode) {
        DeviceMode.PhonePortrait, DeviceMode.TabletPortrait -> {
            LandingScreenPortrait(
                cardCredentialsModifier = cardCredentialsModifier,
                textAlign = textAlign,
                cardCorners = cardCorners,
                paddingValues = paddingValues,
                navigateToLogin = navigateToLogin,
                navigateToRegistration = navigateToRegistration
            )
        }

        DeviceMode.PhoneLandscape, DeviceMode.TabletLandscape -> {
            LandingScreenLandscape(
                textAlign = textAlign,
                cardCorners = cardCorners,
                paddingValues = paddingValues,
                navigateToLogin = navigateToLogin,
                navigateToRegistration = navigateToRegistration
            )
        }
    }
}

@Composable
private fun LandingScreenPortrait(
    cardCredentialsModifier: Modifier = Modifier,
    textAlign: TextAlign,
    cardCorners: CardCorners,
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit,
    navigateToRegistration: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LandingMainImage(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        CardCredentials(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .then(cardCredentialsModifier),
            cardCorners = cardCorners,
            paddingValues = paddingValues,
            textAlign = textAlign,
            onClickGetStarted = navigateToRegistration,
            onClickLogIn = navigateToLogin
        )
    }
}


@Composable
private fun LandingScreenLandscape(
    textAlign: TextAlign,
    cardCorners: CardCorners,
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit,
    navigateToRegistration: () -> Unit
) {
    Row(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LandingMainImage(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.5f),
            contentScale = ContentScale.FillHeight
        )

        CardCredentials(
            modifier = Modifier.weight(0.5f),
            cardCorners = cardCorners,
            paddingValues = paddingValues,
            textAlign = textAlign,
            onClickGetStarted = navigateToRegistration,
            onClickLogIn = navigateToLogin
        )
    }
}