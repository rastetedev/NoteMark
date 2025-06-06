package com.raulastete.notemark.presentation.screens.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.components.NoteMarkPasswordTextField
import com.raulastete.notemark.presentation.designsystem.components.NoteMarkTextField
import com.raulastete.notemark.presentation.designsystem.components.PrimaryButton
import com.raulastete.notemark.presentation.designsystem.components.TertiaryButton
import com.raulastete.notemark.presentation.screens.login.LoginState

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    state: LoginState,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onClickLogin: () -> Unit,
    onClickDontHaveAccount: () -> Unit,
) {
    Column(modifier = modifier) {
        NoteMarkTextField(
            value = state.email,
            onValueChange = onEmailChange,
            title = stringResource(R.string.email_label),
            hint = stringResource(R.string.email_placeholder),
        )

        Spacer(Modifier.height(16.dp))

        NoteMarkPasswordTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = {},
            title = stringResource(R.string.password_label),
            hint = stringResource(R.string.password_placeholder),
        )

        Spacer(Modifier.height(24.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.log_in_button),
            onClick = onClickLogin,
            enabled = false
        )

        Spacer(Modifier.height(24.dp))

        TertiaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.dont_have_account_button),
            onClick = onClickDontHaveAccount
        )
    }
}