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

    when (deviceMode) {
        DeviceMode.PhonePortrait -> {
            LandingScreenPhonePortrait(
                navigateToLogin = navigateToLogin,
                navigateToRegistration = navigateToRegistration
            )
        }

        DeviceMode.PhoneLandscape -> {
            LandingScreenPhoneLandscape(
                navigateToLogin = navigateToLogin,
                navigateToRegistration = navigateToRegistration
            )
        }

        DeviceMode.TabletPortrait -> {
            LandingScreenTabletPortrait(
                navigateToLogin = navigateToLogin,
                navigateToRegistration = navigateToRegistration
            )
        }

        DeviceMode.TabletLandscape -> {
            LandingScreenTabletLandscape(
                navigateToLogin = navigateToLogin,
                navigateToRegistration = navigateToRegistration
            )
        }
    }
}

@Composable
private fun LandingScreenPhonePortrait(
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
            modifier = Modifier.align(Alignment.BottomCenter),
            cardCorners = CardCorners(
                topStart = 20.dp,
                topEnd = 20.dp
            ),
            paddingValues = PaddingValues(vertical = 32.dp, horizontal = 16.dp),
            onClickGetStarted = navigateToRegistration,
            onClickLogIn = navigateToLogin
        )
    }
}

@Composable
private fun LandingScreenPhoneLandscape(
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
            cardCorners = CardCorners(
                topStart = 20.dp,
                bottomStart = 20.dp
            ),
            paddingValues = PaddingValues(start = 60.dp, end = 40.dp, top = 40.dp, bottom = 40.dp),
            onClickGetStarted = navigateToRegistration,
            onClickLogIn = navigateToLogin
        )
    }
}

@Composable
private fun LandingScreenTabletPortrait(
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
            modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 60.dp),
            cardCorners = CardCorners(
                topStart = 24.dp,
                topEnd = 24.dp
            ),
            paddingValues = PaddingValues(48.dp),
            textAlignment = TextAlign.Center,
            onClickGetStarted = navigateToRegistration,
            onClickLogIn = navigateToLogin
        )
    }
}

@Composable
private fun LandingScreenTabletLandscape(
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
            modifier = Modifier.weight(0.4f),
            cardCorners = CardCorners(
                topStart = 20.dp,
                bottomStart = 20.dp
            ),
            textAlignment = TextAlign.Center,
            paddingValues = PaddingValues(start = 60.dp, end = 40.dp, top = 40.dp, bottom = 40.dp),
            onClickGetStarted = navigateToRegistration,
            onClickLogIn = navigateToLogin
        )
    }
}