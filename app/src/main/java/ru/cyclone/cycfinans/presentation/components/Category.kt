package ru.cyclone.cycfinans.presentation.components

import android.view.KeyEvent.KEYCODE_ENTER
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import java.util.Locale

object Categories {
    private val categories = listOf(
        "Food",
        "Clothes",
        "Taxes",
        "Entertainment",
        "Others",
        "Custom"
    )
    private val categoriesRU = listOf(
        "Еда",
        "Одежда",
        "Налоги",
        "Развлечения",
        "Другое",
        "Новое"
    )

    fun getAll(locale: Locale) : List<String> {
        println(locale.language)
        return when (locale.language) {
            "ru" -> categoriesRU
            else -> categories
        }
    }

    fun currentLocation() : Locale {
        return Locale.getDefault()
    }
}

@Composable
fun CategoryChooseDialog(
    show: MutableState<Boolean>,
    onDismiss: () -> Unit,
    category_picker: MutableState<String>
) {
    val c = remember { mutableStateOf("") }
    if (show.value){
        val categories = Categories.getAll(Categories.currentLocation())
        Dialog(
            onDismissRequest = onDismiss) {
            Column {
                for (category in categories) {
                    if (category != categories.last()) {
                        TextButton(
                            onClick = { category_picker.value = category; onDismiss() }) {
                            Text(text = category)
                        }
                    } else {
                        TextField(
                            label = { Text(text = category) },
                            value = c.value,
                            onValueChange = { c.value = it },
                            singleLine = true,
                            keyboardActions = KeyboardActions(
                                onDone = { category_picker.value = c.value; onDismiss() }
                            ),
                            modifier = Modifier
                                .onKeyEvent { keyEvent ->
                                    if (keyEvent.nativeKeyEvent.keyCode == KEYCODE_ENTER) {
                                        category_picker.value = c.value; onDismiss()
                                    }
                                    false
                                }
                                .fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                autoCorrect = true,
                                capitalization = KeyboardCapitalization.Sentences,
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                            ),
                        )
                    }
                }
            }
        }
    }
}