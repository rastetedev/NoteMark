package com.raulastete.notemark.presentation.screens.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun LimitCharactersLengthText(modifier: Modifier = Modifier, text: String, limit: Int, style: TextStyle) {

    Text(
        modifier = modifier,
        text = buildString {
            append(text.trim().take(limit))
            if(text.length > limit){
                append("...")
            }
        },
        style = style
    )
}