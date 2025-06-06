package com.raulastete.notemark.presentation.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.core.NoteMarkTheme

@Composable
fun NoteMarkPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    title: String,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    icon: ImageVector = ImageVector.vectorResource(R.drawable.eye),
    error: String? = null,
    additionalInfo: String? = null,
) {
    NoteMarkTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        hint = hint,
        title = title,
        error = error,
        additionalInfo = additionalInfo,
        endIcon = icon,
        hideText = isPasswordVisible.not(),
        onClickEndIcon = onTogglePasswordVisibility
    )
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkPasswordTextFieldPreview() {
    NoteMarkTheme {
        NoteMarkPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "password123",
            onValueChange = {},
            hint = "Enter your password",
            title = "Password",
            isPasswordVisible = false,
            onTogglePasswordVisibility = {},
        )
    }
}