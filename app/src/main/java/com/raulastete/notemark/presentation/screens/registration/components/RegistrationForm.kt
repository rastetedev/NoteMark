package com.raulastete.notemark.presentation.screens.registration.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.components.NoteMarkPasswordTextField
import com.raulastete.notemark.presentation.designsystem.components.NoteMarkTextField
import com.raulastete.notemark.presentation.designsystem.components.PrimaryButton
import com.raulastete.notemark.presentation.designsystem.components.TertiaryButton
import com.raulastete.notemark.presentation.screens.registration.RegistrationState

@Composable
fun RegistrationForm(
    modifier: Modifier = Modifier,
    state: RegistrationState,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmationChange: (String) -> Unit,
    onClickRegistration: () -> Unit = {},
    onClickLogin: () -> Unit
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        NoteMarkTextField(
            value = state.username,
            onValueChange = onUsernameChange,
            title = stringResource(R.string.username_label),
            hint = stringResource(R.string.username_placeholder),
            error = state.usernameError?.asString(context)
        )

        Spacer(Modifier.height(16.dp))

        NoteMarkTextField(
            value = state.email,
            onValueChange = onEmailChange,
            title = stringResource(R.string.email_label),
            hint = stringResource(R.string.email_placeholder),
            error = state.emailError?.asString(context)
        )

        Spacer(Modifier.height(16.dp))

        NoteMarkPasswordTextField(
            state = state.password,
            title = stringResource(R.string.password_label),
            hint = stringResource(R.string.password_placeholder),
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = {},
            error = state.passwordError?.asString(context)
        )

        Spacer(Modifier.height(16.dp))

        NoteMarkPasswordTextField(
            state = state.passwordConfirmation,
            title = stringResource(R.string.password_confirmation_label),
            hint = stringResource(R.string.password_placeholder),
            isPasswordVisible = state.isPasswordConfirmationVisible,
            onTogglePasswordVisibility = {},
            error = state.passwordConfirmationError?.asString(context)
        )

        Spacer(Modifier.height(24.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.create_account_button),
            onClick = onClickRegistration,
            enabled = false
        )

        Spacer(Modifier.height(24.dp))

        TertiaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.already_have_account_button),
            onClick = onClickLogin
        )
    }
}