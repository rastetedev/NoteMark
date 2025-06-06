package com.raulastete.notemark.presentation.screens.login.components

import android.R.attr.password
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.components.NoteMarkPasswordTextField
import com.raulastete.notemark.presentation.designsystem.components.NoteMarkTextField
import com.raulastete.notemark.presentation.designsystem.components.PrimaryButton
import com.raulastete.notemark.presentation.designsystem.components.TertiaryButton

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    email: String,
    password: TextFieldState,
    onEmailChange: (String) -> Unit = {},
    onClickLogin: () -> Unit,
    onClickDontHaveAccount: () -> Unit,
) {
    Column(modifier = modifier) {
        NoteMarkTextField(
            value = email,
            onValueChange = onEmailChange,
            title = stringResource(R.string.email_label),
            hint = stringResource(R.string.email_placeholder),
        )

        Spacer(Modifier.height(16.dp))

        NoteMarkPasswordTextField(
            state = password,
            isPasswordVisible = false,
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