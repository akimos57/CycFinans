package ru.cyclone.cycfinans.presentation.components

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsVM
import java.sql.Time

@Composable
fun EditPromotion(
    show: Boolean,
    vm: MainDetailsVM,
    onDismiss: () -> Unit,
    promotion: Promotion
){
    if (show) {
        var price by remember { mutableStateOf(promotion.price.toString()) }
        var category by remember { mutableStateOf(promotion.category) }
        var time by remember { mutableStateOf(promotion.time) }

        val c = java.util.Calendar.getInstance()
        val tp = TimePickerDialog(LocalContext.current,
            { _, selectedHour: Int, selectedMinute: Int ->
                time = Time.valueOf("$selectedHour:$selectedMinute:0")
            }, c[java.util.Calendar.HOUR_OF_DAY], c[java.util.Calendar.MINUTE], false)
        
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(horizontal = 36.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.secondary)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TextField(
                            value = price,
                            onValueChange = { price = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                        )
                        TextField(
                            value = category,
                            onValueChange = { category = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                autoCorrect = true,
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                            )
                        )
                        TextButton(onClick = { tp.show() }) {
                            Text(text = "${promotion.time.hours}:${promotion.time.minutes}")
                        }
                        TextButton(
                            onClick = {
                                val color = Color.White.toArgb()
                                vm.addPromotion(
                                    Promotion(
                                        id = promotion.id,
                                        type = promotion.type,
                                        category = category,
                                        colorCategory = color,
                                        price = price.toInt(),
                                        time = time
                                    )
                                ) {
                                    onDismiss()
                                }
                            },
                            modifier = Modifier
                                .fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                            )
                        ) {
                            Text(text = "Add")
                        }
                    }

                }
            }
        }
    }
}