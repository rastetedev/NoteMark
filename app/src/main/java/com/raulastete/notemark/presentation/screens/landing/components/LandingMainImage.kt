package com.raulastete.notemark.presentation.screens.landing.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.raulastete.notemark.R

@Composable
fun LandingMainImage(modifier: Modifier = Modifier, contentScale: ContentScale) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.landing_image),
        contentDescription = null,
        contentScale = contentScale
    )
}