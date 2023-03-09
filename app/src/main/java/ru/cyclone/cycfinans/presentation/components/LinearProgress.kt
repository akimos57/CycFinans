package ru.cyclone.cycfinans.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.cyclone.cycfinans.presentation.ui.theme.fab1

@Composable
fun LinearProgress(
    progress: Float
) {
    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier
            .height(12.dp)
            .clip(RoundedCornerShape(6.dp)),
        color = fab1
    )
}