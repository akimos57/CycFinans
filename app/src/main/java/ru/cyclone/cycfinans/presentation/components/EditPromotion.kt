@file:Suppress("DEPRECATION")

package ru.cyclone.cycfinans.presentation.components

import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsScreenVM
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun EditPromotion(
    show: Boolean,
    vm: MainDetailsScreenVM,
    onDismiss: () -> Unit,
    promotion: Promotion,
    date: Long,
){
    if (show) {
        var price by remember { mutableStateOf(promotion.price.toString()) }
        val category = remember { mutableStateOf(promotion.category) }
        var time by remember { mutableStateOf(Time(date)) }
        val showDialog = remember { mutableStateOf(false) }
        val preferencesController = PreferencesController()

        val c = Calendar.getInstance()
        val tp = TimePickerDialog(LocalContext.current,
            { _, selectedHour: Int, selectedMinute: Int ->
                val q = Calendar.getInstance()
                q.timeInMillis = date
                q.set(Calendar.HOUR_OF_DAY, selectedHour)
                q.set(Calendar.MINUTE, selectedMinute)
                time = Time(q.timeInMillis)
            }, c[Calendar.HOUR_OF_DAY], c[Calendar.MINUTE], true)
        
        CategoryChooseDialog(show = showDialog, onDismiss = { showDialog.value = false }, category_picker = category, type = promotion.type)
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(220.dp)
                        .padding(horizontal = 36.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colors.secondary)
                ) {
                    Column {
                        TextField(
                            value = if (price == "0") "" else price,
                            onValueChange = { price = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.secondary
                            ),
                            placeholder = {
                                Text(
                                    text = "Введите значение",
                                    fontSize = 16.sp
                                )
                            }
                        )
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = { tp.show() }
                        ) {
                            Text(
                                text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(time),
                                color = MaterialTheme.colors.primaryVariant
                            )
                        }
                        TextButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = { showDialog.value = true }
                        ) {
                            Text(text = category.value.ifEmpty { "Выберите категорию" })
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            OutlinedButton(
//                                modifier = Modifier.padding(end = 20.dp),
                                shape = CircleShape,
                                border = BorderStroke(1.dp, color = Color.Transparent),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    backgroundColor = MaterialTheme.colors.secondary,
                                ),
                                onClick = { showDialog.value = false }
                            ) {
                                Text(
                                    text = "Отмена",
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    val color = Color.White.toArgb()
                                    if (price != "0") {
                                        vm.addPromotion(
                                            Promotion(
                                                id = promotion.id,
                                                type = promotion.type,
                                                category = category.value,
                                                colorCategory = color,
                                                price = price.toInt(),
                                                time = time
                                            ),
                                            onSuccess = {
                                                if (!Categories.getAll(
                                                        Locale.getDefault(),
                                                        promotion.type,
                                                    )
                                                        .contains(category.value) and category.value.isNotEmpty()
                                                ) {
                                                    val result = Klaxon().toJsonString(
                                                        Category(
                                                            category.value,
                                                            promotion.type,
                                                            Locale.getDefault().language
                                                        )
                                                    )
                                                    preferencesController.fileNameList.add(result)
                                                    preferencesController.saveLists()
                                                }
                                            }
                                        )
                                    }
                                    onDismiss()
                                },
//                            modifier = Modifier.padding(end = 20.dp), 
                                shape = CircleShape,
                                colors = ButtonDefaults.outlinedButtonColors(
                                    backgroundColor = fab1,
                                )
                            ) {
                                Text(
                                    text = "Сохранить",
                                    color = MaterialTheme.colors.primaryVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}