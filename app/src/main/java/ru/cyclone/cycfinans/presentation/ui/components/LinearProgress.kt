package ru.cyclone.cycfinans.presentation.ui.components

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
import java.math.BigDecimal
import java.math.MathContext

@Composable
fun LinearProgress(
    progress: BigDecimal,
    color: Color,
    width: Dp,
    height: Dp
) {
    LinearProgressIndicator(
        progress = progress.round(MathContext(2)).toFloat(),
        modifier = Modifier
            .height(height)
            .width(width)
            .clip(RoundedCornerShape(6.dp)),
        color = color
    )
}