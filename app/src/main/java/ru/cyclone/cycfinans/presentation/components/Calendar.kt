package ru.cyclone.cycfinans.presentation.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import java.time.Month
import java.time.format.TextStyle
import java.util.*

@Composable
fun Calendar(
    visible: Boolean,
    currentMonth: Int,
    currentYear: Int,
    confirmButtonClicked: (Int, Int) -> Unit,
    cancelClicked: () -> Unit,
    onDismiss: () -> Unit
) {
    val months = List<String>(12) { index ->
        Month.of(index + 1).getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
    }

    var month by remember {
        mutableStateOf(months[currentMonth])
    }

    var year by remember {
        mutableStateOf(currentYear)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    if (visible) {
        AlertDialog(
            backgroundColor = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(10),
            title = {

            },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year--
                                    }
                                ),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "left",
                            tint = MaterialTheme.colors.primaryVariant
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            text = year.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primaryVariant
                        )

                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(-90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year++
                                    }
                                ),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "right",
                            tint = MaterialTheme.colors.primaryVariant
                        )
                    }


                    Card(
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth(),
                        elevation = 0.dp
                    ) {

                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.secondary),
                            mainAxisSpacing = 16.dp,
                            crossAxisSpacing = 16.dp,
                            mainAxisAlignment = MainAxisAlignment.Center,
                            crossAxisAlignment = FlowCrossAxisAlignment.Center
                        ) {

                            months.forEach {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = interactionSource,
                                            onClick = {
                                                month = it
                                            }
                                        )
                                        .background(MaterialTheme.colors.secondary),
                                    contentAlignment = Alignment.Center
                                ) {

                                    val animatedSize by animateDpAsState(
                                        targetValue = if (month == it) 60.dp else 0.dp,
                                        animationSpec = tween(
                                            durationMillis = 200,
                                            easing = LinearOutSlowInEasing
                                        )
                                    )

                                    Box(
                                        modifier = Modifier
                                            .size(animatedSize)
                                            .background(
                                                color = if (month == it) fab1 else MaterialTheme.colors.secondary,
                                                shape = CircleShape
                                            )
                                    )

                                    Text(
                                        text = it,
                                        fontWeight = FontWeight.Medium,
                                        color = if (month == it) MaterialTheme.colors.background else MaterialTheme.colors.primaryVariant
                                    )
                                }
                            }
                        }
                    }
//                    }
                }
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    OutlinedButton(
                        modifier = Modifier
                            .padding(end = 20.dp),
                        onClick = {
                            cancelClicked()
                        },
                        shape = CircleShape,
                        border = BorderStroke(1.dp, color = Color.Transparent),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = MaterialTheme.colors.secondary
                        )
                    ) {
                        Text(
                            text = "Отмена",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colors.primaryVariant
                        )
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .padding(end = 20.dp),
                        onClick = {
                            confirmButtonClicked(
                                months.indexOf(month) + 1,
                                year
                            )
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = fab1
                        )
                    ) {
                        Text(
                            text = "OK",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colors.primaryVariant
                        )
                    }

                }
            },
            onDismissRequest = onDismiss
        )
    }
}



















