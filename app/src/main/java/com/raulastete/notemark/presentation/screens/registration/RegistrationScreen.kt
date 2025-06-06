package com.raulastete.notemark.presentation.screens.registration

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
import com.raulastete.notemark.presentation.screens.registration.components.RegistrationForm
import com.raulastete.notemark.presentation.screens.registration.components.RegistrationScreenHeader
import com.raulastete.notemark.presentation.utils.DeviceMode
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationRoot(
    deviceMode: DeviceMode,
    viewModel: RegistrationViewModel = koinViewModel(),
    navigateToLogin: () -> Unit
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
                RegistrationScreenPhonePortrait(
                    state = state,
                    onUsernameChange = {
                        viewModel.onAction(RegistrationAction.OnUsernameChange(it))
                    },
                    onEmailChange = {
                        viewModel.onAction(RegistrationAction.OnEmailChange(it))
                    },
                    onPasswordChange = {
                        viewModel.onAction(RegistrationAction.OnPasswordChange(it))
                    },
                    onPasswordConfirmationChange = {
                        viewModel.onAction(RegistrationAction.OnPasswordConfirmationChange(it))
                    },
                    onClickRegistration = {
                        viewModel.onAction(RegistrationAction.OnClickRegistration)
                    },
                    onClickLogin = navigateToLogin
                )
            }

            DeviceMode.PhoneLandscape -> {
                RegistrationScreenPhoneLandscape(
                    state = state,
                    onUsernameChange = {
                        viewModel.onAction(RegistrationAction.OnUsernameChange(it))
                    },
                    onEmailChange = {
                        viewModel.onAction(RegistrationAction.OnEmailChange(it))
                    },
                    onPasswordChange = {
                        viewModel.onAction(RegistrationAction.OnPasswordChange(it))
                    },
                    onPasswordConfirmationChange = {
                        viewModel.onAction(RegistrationAction.OnPasswordConfirmationChange(it))
                    },
                    onClickRegistration = {
                        viewModel.onAction(RegistrationAction.OnClickRegistration)
                    },
                    onClickLogin = navigateToLogin
                )
            }

            DeviceMode.TabletPortrait -> {
                RegistrationScreenTabletPortrait(
                    state = state,
                    onUsernameChange = {
                        viewModel.onAction(RegistrationAction.OnUsernameChange(it))
                    },
                    onEmailChange = {
                        viewModel.onAction(RegistrationAction.OnEmailChange(it))
                    },
                    onPasswordChange = {
                        viewModel.onAction(RegistrationAction.OnPasswordChange(it))
                    },
                    onPasswordConfirmationChange = {
                        viewModel.onAction(RegistrationAction.OnPasswordConfirmationChange(it))
                    },
                    onClickRegistration = {
                        viewModel.onAction(RegistrationAction.OnClickRegistration)
                    },
                    onClickLogin = navigateToLogin
                )
            }

            DeviceMode.TabletLandscape -> {
                RegistrationScreenTabletLandscape(
                    state = state,
                    onUsernameChange = {
                        viewModel.onAction(RegistrationAction.OnUsernameChange(it))
                    },
                    onEmailChange = {
                        viewModel.onAction(RegistrationAction.OnEmailChange(it))
                    },
                    onPasswordChange = {
                        viewModel.onAction(RegistrationAction.OnPasswordChange(it))
                    },
                    onPasswordConfirmationChange = {
                        viewModel.onAction(RegistrationAction.OnPasswordConfirmationChange(it))
                    },
                    onClickRegistration = {
                        viewModel.onAction(RegistrationAction.OnClickRegistration)
                    },
                    onClickLogin = navigateToLogin
                )
            }
        }
    }
}

@Composable
fun RegistrationScreenPhonePortrait(
    state: RegistrationState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onClickRegistration: () -> Unit,
    onClickLogin: () -> Unit
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
        RegistrationScreenHeader(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(40.dp))

        RegistrationForm(
            modifier = Modifier.fillMaxWidth(),
            username = state.username,
            email = state.email,
            password = state.password,
            passwordConfirmation = state.passwordConfirmation,
            onUsernameChange = onUsernameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onPasswordConfirmationChange = onPasswordConfirmationChange,
            onClickRegistration = onClickRegistration,
            onClickLogin = onClickLogin
        )
    }
}

@Composable
fun RegistrationScreenPhoneLandscape(
    state: RegistrationState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onClickRegistration: () -> Unit,
    onClickLogin: () -> Unit
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
        RegistrationScreenHeader(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 40.dp)
        )

        RegistrationForm(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
                .verticalScroll(rememberScrollState()),
            username = state.username,
            email = state.email,
            password = state.password,
            passwordConfirmation = state.passwordConfirmation,
            onUsernameChange = onUsernameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onPasswordConfirmationChange = onPasswordConfirmationChange,
            onClickRegistration = onClickRegistration,
            onClickLogin = onClickLogin
        )
    }
}

@Composable
fun RegistrationScreenTabletPortrait(
    state: RegistrationState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onClickRegistration: () -> Unit,
    onClickLogin: () -> Unit
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
        RegistrationScreenHeader(
            modifier = Modifier.fillMaxWidth(),
            textAlignment = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))

        RegistrationForm(
            modifier = Modifier.fillMaxWidth(),
            username = state.username,
            email = state.email,
            password = state.password,
            passwordConfirmation = state.passwordConfirmation,
            onUsernameChange = onUsernameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onPasswordConfirmationChange = onPasswordConfirmationChange,
            onClickRegistration = onClickRegistration,
            onClickLogin = onClickLogin
        )
    }
}

@Composable
fun RegistrationScreenTabletLandscape(
    state: RegistrationState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onClickRegistration: () -> Unit,
    onClickLogin: () -> Unit
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
        RegistrationScreenHeader(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 40.dp),
            textAlignment = TextAlign.Center
        )

        RegistrationForm(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
                .verticalScroll(rememberScrollState()),
            username = state.username,
            email = state.email,
            password = state.password,
            passwordConfirmation = state.passwordConfirmation,
            onUsernameChange = onUsernameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onPasswordConfirmationChange = onPasswordConfirmationChange,
            onClickRegistration = onClickRegistration,
            onClickLogin = onClickLogin
        )
    }
}