package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import ru.cyclone.cycfinans.presentation.ui.theme.*
import ru.cyclone.cycnote.R
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalTextApi::class, ExperimentalUnitApi::class)
@Composable
fun ChartDonut(
    data: Map<String, BigDecimal>,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 2000,
    animationState: MutableState<Boolean>
) {
    val currency = stringResource(id = R.string.dollar)
    val totalSum = data.values.sumOf { it }
    val floatValue = data.values.map { 360F * it.toFloat() / totalSum.toFloat() }

    val colors = mutableListOf(
        Purple200,
        fab1,
        fab2,
        gold,
        Purple700,
        diagram1,
        diagram2,
        diagram3,
        diagram4,
        diagram5,
        diagram6,
        diagram7,
        diagram8,
        diagram9,
        diagram10,
        diagram11,
        diagram12,
        diagram13,
        diagram14,
    )

    val dataSize = data.values.size
    if (dataSize > colors.size) {
        colors += Color(Random().nextInt().absoluteValue)
    }

    val extraSize = data.values.size - colors.size
    if (data.values.size > colors.size) {
        colors += List(extraSize) { Color(Random().nextInt().absoluteValue) }
    }

    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationState.value) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationState.value) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateScaling by animateDpAsState(
        targetValue = if (animationState.value) 24.dp else 0.dp,
        animationSpec = tween(
            animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationState.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text = NumberFormat.getNumberInstance(Locale.US).format(totalSum).replace(',', ' ')
        text = if (text.isEmpty() or (text == "0"))
            ""
        else "$text $currency"
        val textStyle = TextStyle(
            fontSize = TextUnit(animateScaling.value, TextUnitType.Sp),
            color = MaterialTheme.colors.primaryVariant
        )
        val textMeasurer = rememberTextMeasurer()
        val textLayoutResult: TextLayoutResult =
            textMeasurer.measure(
                text = AnnotatedString(text),
                style = textStyle
            )
        val textSize = textLayoutResult.size
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
                        startAngle = lastValue,
                        sweepAngle = value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ){
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawText(
                    textMeasurer = textMeasurer,
                    topLeft = Offset(
                        (canvasWidth - textSize.width) / 2f,
                        (canvasHeight - textSize.height) / 2f
                    ),
                    text = text,
                    style = textStyle
                )
            }
        }
        DetailsPieChart(
            data = data,
            colors = colors,
            totalSum = totalSum
        )
    }
}

@Composable
fun DetailsPieChart(
    data: Map<String, BigDecimal>,
    colors: List<Color>,
    totalSum: BigDecimal
) {
    Column(
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        data.values.forEachIndexed { index, value ->
            DetailsPieChartItem(
                data = Pair(data.keys.elementAt(index), value),
                color = colors[index],
                totalSum = totalSum
            )
        }
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, BigDecimal>,
    color: Color,
    totalSum: BigDecimal
) {
    val currency = stringResource(id = R.string.dollar)
    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 12.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 15.dp),
                        text = data.first.ifEmpty { "Другое" },
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                    )
                    val price = NumberFormat.getNumberInstance(Locale.US).format(data.second).replace(',', ' ')
                    Text(
                        modifier = Modifier
                            .padding(start = 15.dp),
                        text = "$price $currency",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            val progress = data.second.divide(totalSum, 2, RoundingMode.HALF_UP)

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.7f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgress(
                    progress = progress,
                    color = color,
                    width = 130.dp,
                    height = 10.dp
                )
            }

            val percent = progress * BigDecimal(100)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "$percent %",
                    fontSize = 14.sp
                )
            }

        }
    }
}