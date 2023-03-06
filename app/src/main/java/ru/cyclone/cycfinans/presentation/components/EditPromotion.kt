package ru.cyclone.cycfinans.presentation.components

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsVM

@Composable
fun EditPromotion(
    show: Boolean,
    vm: MainDetailsVM,
    onDismiss: () -> Unit
){
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    if (show) {
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                        )
                        TextField(
                            value = category,
                            onValueChange = { category = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                            )
                        )
                        TextButton(
                            onClick = {
                                val color = Color.White.toArgb()
                                val currentTime = java.sql.Time(System.currentTimeMillis())
                                vm.addPromotion(
                                    Promotion(
                                        type = true,
                                        category = category,
                                        colorCategory = color,
                                        price = price.toInt(),
                                        time = currentTime
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
                            Text(text = "Категория")
                        }
                    }

                }
            }
        }
    }
}