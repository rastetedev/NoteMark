package com.raulastete.notemark.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp)
    ) {
        when (deviceMode) {
            DeviceMode.PhonePortrait -> {
                LoginScreenPhonePortrait(
                    state = state,
                    onEmailChange = {
                        viewModel.onAction(LoginAction.OnEmailChange(it))
                    },
                    onClickLogin = {
                        viewModel.onAction(LoginAction.OnClickLogin)
                    },
                    navigateToRegistration = navigateToRegistration
                )
            }

            DeviceMode.PhoneLandscape -> {
                LoginScreenPhoneLandscape(
                    state = state,
                    onEmailChange = {
                        viewModel.onAction(LoginAction.OnEmailChange(it))
                    },
                    onClickLogin = {
                        viewModel.onAction(LoginAction.OnClickLogin)
                    },
                    navigateToRegistration = navigateToRegistration
                )
            }

            DeviceMode.TabletPortrait -> {
                LoginScreenTabletPortrait(
                    state = state,
                    onEmailChange = {
                        viewModel.onAction(LoginAction.OnEmailChange(it))
                    },
                    onPasswordChange = {
                        viewModel.onAction(LoginAction.OnPasswordChange(it))
                    },
                    onClickLogin = {
                        viewModel.onAction(LoginAction.OnClickLogin)
                    },
                    navigateToRegistration = navigateToRegistration
                )
            }

            DeviceMode.TabletLandscape -> {
                LoginScreenTabletLandscape(
                    state = state,
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
fun LoginScreenPhonePortrait(
    state: LoginState,
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
            .padding(horizontal = 16.dp, vertical = 32.dp),
    ) {
        LoginScreenHeader(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(40.dp))

        LoginForm(
            modifier = Modifier.fillMaxWidth(),
            email = state.email,
            password = state.password,
            onEmailChange = onEmailChange,
            onClickLogin = onClickLogin,
            onClickDontHaveAccount = navigateToRegistration
        )
    }
}

@Composable
fun LoginScreenPhoneLandscape(
    state: LoginState,
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
            .padding(horizontal = 16.dp, vertical = 32.dp),
    ) {
        LoginScreenHeader(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 40.dp)
        )

        LoginForm(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
                .verticalScroll(rememberScrollState()),
            email = state.email,
            password = state.password,
            onEmailChange = onEmailChange,
            onClickLogin = onClickLogin,
            onClickDontHaveAccount = navigateToRegistration
        )
    }
}

@Composable
fun LoginScreenTabletPortrait(
    state: LoginState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
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
            .padding(horizontal = 120.dp, vertical = 100.dp),
    ) {
        LoginScreenHeader(
            modifier = Modifier.fillMaxWidth(),
            textAlignment = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        LoginForm(
            modifier = Modifier.fillMaxWidth(),
            email = state.email,
            password = state.password,
            onEmailChange = onEmailChange,
            onClickLogin = onClickLogin,
            onClickDontHaveAccount = navigateToRegistration
        )
    }
}

@Composable
fun LoginScreenTabletLandscape(
    state: LoginState,
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
            .padding(horizontal = 100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LoginScreenHeader(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 40.dp),
            textAlignment = TextAlign.Center
        )

        LoginForm(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
                .verticalScroll(rememberScrollState()),
            email = state.email,
            password = state.password,
            onEmailChange = onEmailChange,
            onClickLogin = onClickLogin,
            onClickDontHaveAccount = navigateToRegistration
        )
    }
}