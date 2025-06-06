package com.raulastete.notemark.presentation.designsystem.core

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Purple,
    background = LandingBackground,
    onPrimary = White,
    onPrimaryContainer = White12,
    primaryContainer = Purple10,
    surface = GreyLight,
    surfaceVariant = White,
    onSurface = Black,
    onSurfaceVariant = GreyDark,
    error = Red,

)

@Composable
fun NoteMarkTheme(
    content: @Composable () -> Unit
) {

    SetStatusBarWhiteIcons()

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}


@Composable
fun SetStatusBarWhiteIcons() {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as? Activity)?.window ?: return@SideEffect
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }
}