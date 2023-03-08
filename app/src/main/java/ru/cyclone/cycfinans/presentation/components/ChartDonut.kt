package ru.cyclone.cycfinans.presentation.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cyclone.cycfinans.presentation.ui.theme.*

@Composable
fun ChartDonut(
    data: Map<String, Int>,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 3000
) {
    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360*values.toFloat() / totalSum.toFloat())
    }

    val colors = listOf(
        Purple200,
        fab1,
        fab2,
        gold,
        Purple700
    )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }
        DetailsPieChart(
            data = data,
            colors = colors
        )
    }
}

@Composable
fun DetailsPieChart(
    data: Map<String, Int>,
    colors: List<Color>
) {
    Column(
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxWidth()
    ) {

        data.values.forEachIndexed { index, value ->
            DetailsPieChartItem(
                data = Pair(data.keys.elementAt(index), value),
                color = colors[index]
            )
        }

    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Int>,
    height: Dp = 45.dp,
    color: Color
) {
    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 30.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .size(height)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier
                        .padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
























