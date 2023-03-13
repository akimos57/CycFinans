package ru.cyclone.cycfinans.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.cyclone.cycfinans.presentation.ui.theme.fab1

@Composable
fun LinearProgress(
    progress: Float,
    color: Color,
    width: Dp
) {
    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier
            .height(12.dp)
            .width(width)
            .clip(RoundedCornerShape(6.dp)),
        color = color
    )
}