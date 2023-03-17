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
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.domain.model.CalendarInput
import ru.cyclone.cycfinans.presentation.ui.theme.blue
import ru.cyclone.cycfinans.presentation.ui.theme.fab1

private const val CALENDAR_ROWS = 5
private const val CALENDAR_COLUMNS = 7
@Composable
fun MyCalendar(
    modifier: Modifier = Modifier,
    calendarInput: List<CalendarInput>,
    onDayClick:(Int) -> Unit,
    strokeWidth: Float = 2f,
    month: String
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
                            val column = (offset.x / canvasSize.width * CALENDAR_COLUMNS).toInt() + 1
                            val row = (offset.y / canvasSize.height * CALENDAR_ROWS).toInt() + 1
                            val day = column + (row-1) * CALENDAR_COLUMNS
                            if (day <= calendarInput.size) {
                                onDayClick(day)
                                clickAnimationOffset = offset
                                scope.launch {
                                    animate(0f,225f, animationSpec = tween(300)) { value, _ ->
                                        animationRadius = value
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            val canvasHeight = size.height
            val canvasWidth = size.width
            canvasSize = Size(canvasWidth, canvasHeight)
            val ySteps = canvasHeight / CALENDAR_ROWS
            val xSteps = canvasWidth / CALENDAR_COLUMNS

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
//            for(i in 1 until CALENDAR_ROWS) {
//                drawLine(
//                    color = blue,
//                    start = Offset(0f,ySteps*i),
//                    end = Offset(canvasWidth,ySteps*i),
//                    strokeWidth = 0f
//                )
//            }
//            for(i in 1 until CALENDAR_COLUMNS) {
//                drawLine(
//                    color = blue,
//                    start = Offset(xSteps*i,0f),
//                    end = Offset(xSteps*i,canvasHeight),
//                    strokeWidth = 0f
//                )
//            }
            val textHeight = 16.dp.toPx()
            for(i in calendarInput.indices) {
                val textPositionX = xSteps * (i% CALENDAR_COLUMNS) + strokeWidth
                val textPositionY = (i / CALENDAR_COLUMNS) * ySteps + textHeight + strokeWidth/2
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "${i+1}",
                        textPositionX,
                        textPositionY,
                        Paint().apply {
                            textSize = textHeight
                            color = color2.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
    }
}
