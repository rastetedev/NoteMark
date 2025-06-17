package com.raulastete.notemark.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.raulastete.notemark.presentation.designsystem.core.FirstGradient
import com.raulastete.notemark.presentation.designsystem.core.SecondGradient

@Composable
fun NoteMarkFab(
    onClick: () -> Unit,
    size: Dp = 56.dp,
    icon: ImageVector = Icons.Default.Add
) {
    FloatingActionButton(
        onClick = onClick,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = Transparent
    ) {
        Box(
            Modifier
                .size(size)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            FirstGradient,
                            SecondGradient,
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null)
        }
    }
}