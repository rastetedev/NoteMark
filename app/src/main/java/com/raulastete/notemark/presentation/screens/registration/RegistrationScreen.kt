package com.raulastete.notemark.presentation.screens.registration

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.components.NoteMarkMessageSnackbar
import com.raulastete.notemark.presentation.screens.registration.components.RegistrationForm
import com.raulastete.notemark.presentation.screens.registration.components.RegistrationScreenHeader
import com.raulastete.notemark.presentation.utils.DeviceMode
import com.raulastete.notemark.presentation.utils.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationRoot(
    deviceMode: DeviceMode,
    viewModel: RegistrationViewModel = koinViewModel(),
    navigateToLogin: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            RegistrationEvent.OnRegistrationFail -> {
                keyboardController?.hide()
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.registration_error_message),
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }

            RegistrationEvent.OnRegistrationSuccess -> {
                keyboardController?.hide()
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.registration_success_message),
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    val padding = when (deviceMode) {
        DeviceMode.PhonePortrait, DeviceMode.PhoneLandscape -> PaddingValues(
            horizontal = 16.dp,
            vertical = 32.dp
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
                RegistrationScreenPortrait(
                    state = state,
                    paddingValues = padding,
                    textAlign = textAlign,
                    onUsernameChange = {
                        viewModel.onAction(RegistrationAction.UsernameChange(it))
                    },
                    onEmailChange = {
                        viewModel.onAction(RegistrationAction.EmailChange(it))
                    },
                    onPasswordChange = {
                        viewModel.onAction(RegistrationAction.PasswordChange(it))
                    },
                    onPasswordConfirmationChange = {
                        viewModel.onAction(RegistrationAction.PasswordConfirmationChange(it))
                    },
                    onTogglePasswordVisibility = {
                        viewModel.onAction(RegistrationAction.TogglePasswordVisibility)
                    },
                    onTogglePasswordConfirmationVisibility = {
                        viewModel.onAction(RegistrationAction.TogglePasswordConfirmationVisibility)
                    },
                    onClickRegistration = {
                        viewModel.onAction(RegistrationAction.ClickRegistration)
                    },
                    onClickLogin = navigateToLogin
                )
            }

            DeviceMode.PhoneLandscape, DeviceMode.TabletLandscape -> {
                RegistrationScreenLandscape(
                    state = state,
                    paddingValues = padding,
                    textAlign = textAlign,
                    onUsernameChange = {
                        viewModel.onAction(RegistrationAction.UsernameChange(it))
                    },
                    onEmailChange = {
                        viewModel.onAction(RegistrationAction.EmailChange(it))
                    },
                    onPasswordChange = {
                        viewModel.onAction(RegistrationAction.PasswordChange(it))
                    },
                    onPasswordConfirmationChange = {
                        viewModel.onAction(RegistrationAction.PasswordConfirmationChange(it))
                    },
                    onTogglePasswordVisibility = {
                        viewModel.onAction(RegistrationAction.TogglePasswordVisibility)
                    },
                    onTogglePasswordConfirmationVisibility = {
                        viewModel.onAction(RegistrationAction.TogglePasswordConfirmationVisibility)
                    },
                    onClickRegistration = {
                        viewModel.onAction(RegistrationAction.ClickRegistration)
                    },
                    onClickLogin = navigateToLogin
                )
            }
        }

        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackbarHostState,
            snackbar = { snackbarData ->
                val isErrorMessage =
                    snackbarData.visuals.message != stringResource(id = R.string.registration_success_message)

                NoteMarkMessageSnackbar(
                    snackbarData = snackbarData,
                    isErrorMessage = isErrorMessage,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            },
        )
    }
}

@Composable
fun RegistrationScreenPortrait(
    state: RegistrationState,
    paddingValues: PaddingValues,
    textAlign: TextAlign,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onTogglePasswordConfirmationVisibility: () -> Unit,
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
            .padding(paddingValues),
    ) {
        RegistrationScreenHeader(
            modifier = Modifier.fillMaxWidth(),
            textAlign = textAlign
        )

        Spacer(Modifier.height(40.dp))

        RegistrationForm(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            onUsernameChange = onUsernameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onPasswordConfirmationChange = onPasswordConfirmationChange,
            onClickRegistration = onClickRegistration,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            onTogglePasswordConfirmationVisibility = onTogglePasswordConfirmationVisibility,
            onClickLogin = onClickLogin
        )
    }
}

@Composable
fun RegistrationScreenLandscape(
    state: RegistrationState,
    paddingValues: PaddingValues,
    textAlign: TextAlign,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onTogglePasswordConfirmationVisibility: () -> Unit,
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
            .padding(paddingValues),
    ) {
        RegistrationScreenHeader(
            modifier = Modifier
                .weight(0.5f)
                .padding(start = 40.dp),
            textAlign = textAlign
        )

        RegistrationForm(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 40.dp)
                .verticalScroll(rememberScrollState()),
            state = state,
            onUsernameChange = onUsernameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onPasswordConfirmationChange = onPasswordConfirmationChange,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            onTogglePasswordConfirmationVisibility = onTogglePasswordConfirmationVisibility,
            onClickRegistration = onClickRegistration,
            onClickLogin = onClickLogin
        )
    }
}