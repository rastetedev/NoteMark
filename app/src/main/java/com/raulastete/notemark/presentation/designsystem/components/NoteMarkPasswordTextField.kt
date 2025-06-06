package com.raulastete.notemark.presentation.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.R
import com.raulastete.notemark.presentation.designsystem.core.NoteMarkTheme

@Composable
fun NoteMarkPasswordTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState = rememberTextFieldState(),
    hint: String,
    title: String,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    error: String? = null,
    additionalInfo: String? = null,
) {
    var isFocused by remember { mutableStateOf(false) }

    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isFocused) {
            Color.Transparent
        } else {
            MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(100, 0, LinearEasing),
        label = "Background color"
    )
    val animatedBorderColor by animateColorAsState(
        targetValue = when {
            isFocused -> MaterialTheme.colorScheme.onSurfaceVariant
            error != null -> MaterialTheme.colorScheme.error
            else -> Color.Transparent
        },
        animationSpec = tween(200, 0, LinearEasing),
        label = "Border color"
    )

    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(4.dp))
        BasicSecureTextField(
            state = state,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            textObfuscationMode = if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.RevealLastTyped
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(animatedBackgroundColor)
                .border(
                    width = 1.dp,
                    color = animatedBorderColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            textObfuscationCharacter = '*',
            decorator = { innerBox ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerBox()
                    }
                    IconButton(
                        onClick = onTogglePasswordVisibility,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .padding(start = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) ImageVector.vectorResource(R.drawable.eye) else ImageVector.vectorResource(
                                R.drawable.eye_off
                            ),
                            contentDescription = if (isPasswordVisible) {
                                stringResource(id = R.string.hide_password)
                            } else {
                                stringResource(id = R.string.show_password)
                            },
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        } else if (additionalInfo != null) {
            Text(
                text = additionalInfo,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun NoteMarkPasswordTextFieldPreview() {
    NoteMarkTheme {
        NoteMarkPasswordTextField(
            state = rememberTextFieldState(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = {},
            hint = "password123",
            title = "Password",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}