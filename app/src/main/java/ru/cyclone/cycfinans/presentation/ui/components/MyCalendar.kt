package ru.cyclone.cycfinans.presentation.ui.components

import android.graphics.Paint
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.presentation.ui.theme.blue
import java.time.LocalDate
import java.time.YearMonth

private const val CALENDAR_ROWS = 5
private const val CALENDAR_COLUMNS = 7
@Composable
fun MyCalendar(
    modifier: Modifier = Modifier,
    onDaySelected:(Int) -> Unit,
    strokeWidth: Float = 2f,
    yearMonth: MutableState<YearMonth?>
) {
    var canvasSize by remember {
        mutableStateOf(Size.Zero)
    }
    var clickAnimationOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    var animationRadius by remember {
        mutableStateOf(0f)
    }

    var day by remember { mutableStateOf(LocalDate.now().dayOfMonth) }
    var firstDayOfWeek by remember { mutableStateOf(LocalDate.now().dayOfWeek.value) }

    val scope = rememberCoroutineScope()

    val color2 = MaterialTheme.colors.primaryVariant
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = { offset ->
                            val column = (offset.x / canvasSize.width * CALENDAR_COLUMNS).toInt()
                            val row = (offset.y / canvasSize.height * CALENDAR_ROWS).toInt()
                            day = (column + (row) * CALENDAR_COLUMNS) - firstDayOfWeek + 1

                            if (yearMonth.value!!.isValidDay(day)) {
                                onDaySelected(day)
                                clickAnimationOffset = offset
                                scope.launch {
                                    animate(0f, 225f, animationSpec = tween(300)) { value, _ ->
                                        animationRadius = value
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            canvasSize = Size(size.width, size.height)
            val ySteps = size.height / CALENDAR_ROWS
            val xSteps = size.width / CALENDAR_COLUMNS

            val column = (clickAnimationOffset.x / canvasSize.width * CALENDAR_COLUMNS).toInt() + 1
            val row = (clickAnimationOffset.y / canvasSize.height * CALENDAR_ROWS).toInt() + 1

            val path = Path().apply {
                moveTo((column-1)*xSteps,(row-1)*ySteps)
                lineTo(column*xSteps,(row-1)*ySteps)
                lineTo(column*xSteps,row*ySteps)
                lineTo((column-1)*xSteps,row*ySteps)
                close()
            }

            clipPath(path) {
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(blue.copy(0.8f), blue.copy(0.2f)),
                        center = clickAnimationOffset,
                        radius = animationRadius + 0.1f
                    ),
                    radius = animationRadius + 0.1f,
                    center = clickAnimationOffset
                )
            }

            drawRoundRect(
                blue,
                cornerRadius = CornerRadius(25f,25f),
                style = Stroke(
                    width = strokeWidth
                )
            )
            val textHeight = 16.dp.toPx()
            val calendar = java.util.Calendar.getInstance()
            calendar.set(yearMonth.value!!.year, yearMonth.value!!.monthValue - 1, 1)

            firstDayOfWeek = calendar[java.util.Calendar.DAY_OF_WEEK] - 1
            for(i in 0..34) {
                val textPositionX = (i % CALENDAR_COLUMNS) * xSteps + strokeWidth
                val textPositionY = (i / CALENDAR_COLUMNS) * ySteps + textHeight
                val weekColor = when (i % CALENDAR_COLUMNS) {
                    5 -> Color.Green.toArgb()
                    6 -> Color.Red.toArgb()
                    else -> color2.toArgb()
                }

                if (i in firstDayOfWeek until yearMonth.value!!.lengthOfMonth() + firstDayOfWeek) {
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "${ i - firstDayOfWeek + 1}",
                            textPositionX,
                            textPositionY,
                            Paint().apply {
                                textSize = textHeight
                                color = weekColor
                                isFakeBoldText = true
                            }
                        )
                    }
                }
            }
        }
    }
}
