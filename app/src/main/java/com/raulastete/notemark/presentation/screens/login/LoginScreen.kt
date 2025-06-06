package com.raulastete.notemark.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.notemark.presentation.screens.login.components.LoginForm
import com.raulastete.notemark.presentation.screens.login.components.LoginScreenHeader
import com.raulastete.notemark.presentation.utils.DeviceMode
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    deviceMode: DeviceMode,
    viewModel: LoginViewModel = koinViewModel(),
    navigateToRegistration: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val padding = when (deviceMode) {
        DeviceMode.PhonePortrait, DeviceMode.PhoneLandscape ->
            PaddingValues(horizontal = 16.dp, vertical = 32.dp
        )
        DeviceMode.TabletPortrait -> PaddingValues(horizontal = 120.dp, vertical = 100.dp)
        DeviceMode.TabletLandscape -> PaddingValues(horizontal = 100.dp, vertical = 100.dp)
    }

    val textAlign = when (deviceMode) {
        DeviceMode.PhonePortrait, DeviceMode.PhoneLandscape -> TextAlign.Start
        else -> TextAlign.Center
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp)
    ) {
        when (deviceMode) {
            DeviceMode.PhonePortrait, DeviceMode.TabletPortrait -> {
                LoginScreenPortrait(
                    state = state,
                    paddingValues = padding,
                    textAlign = textAlign,
                    onEmailChange = {
                        viewModel.onAction(LoginAction.OnEmailChange(it))
                    },
                    onClickLogin = {
                        viewModel.onAction(LoginAction.OnClickLogin)
                    },
                    navigateToRegistration = navigateToRegistration
                )
            }

            DeviceMode.PhoneLandscape, DeviceMode.TabletLandscape -> {
                LoginScreenLandscape(
                    state = state,
                    paddingValues = padding,
                    textAlign = textAlign,
                    onEmailChange = {
                        viewModel.onAction(LoginAction.OnEmailChange(it))
                    },
                    onClickLogin = {
                        viewModel.onAction(LoginAction.OnClickLogin)
                    },
                    navigateToRegistration = navigateToRegistration
                )
            }
        }
    }
}

@Composable
fun LoginScreenPortrait(
    state: LoginState,
    paddingValues: PaddingValues,
    textAlign: TextAlign,
    onEmailChange: (String) -> Unit,
    onClickLogin: () -> Unit,
    navigateToRegistration: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .verticalScroll(rememberScrollState())
            .padding(paddingValues),
    ) {
        LoginScreenHeader(
            modifier = Modifier.fillMaxWidth(),
            textAlign = textAlign
        )

        Spacer(Modifier.height(40.dp))

        LoginForm(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            onEmailChange = onEmailChange,
            onClickLogin = onClickLogin,
            onClickDontHaveAccount = navigateToRegistration
        )
    }
}

@Composable
fun LoginScreenLandscape(
    state: LoginState,
    paddingValues: PaddingValues,
    textAlign: TextAlign,
    onEmailChange: (String) -> Unit,
    onClickLogin: () -> Unit,
    navigateToRegistration: () -> Unit
) {

    Row(
        Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(paddingValues),
    ) {
        LoginScreenHeader(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 40.dp),
            textAlign = textAlign
        )

        LoginForm(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
                .verticalScroll(rememberScrollState()),
            state = state,
            onEmailChange = onEmailChange,
            onClickLogin = onClickLogin,
            onClickDontHaveAccount = navigateToRegistration
        )
    }
}