package ru.cyclone.cycfinans.presentation.components

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*

object Categories {
    const val categoryPreferencesKey = "CategoryPreferencesKey"
    private val categories = listOf(
        "Food",
        "Clothes",
        "Taxes",
        "Entertainment",
        "Others"
    )
    private val categoriesRU = listOf(
        "Еда",
        "Одежда",
        "Налоги",
        "Развлечения",
        "Другое"
    )

    private val incomeCategories = listOf(
        "!Food",
        "!Clothes",
        "!Taxes",
        "!Entertainment",
        "!Others"
    )
    private val incomeCategoriesRU = listOf(
        "!Еда",
        "!Одежда",
        "!Налоги",
        "!Развлечения",
        "!Другое"
    )

    fun getAll(
        locale: Locale,
        type: Boolean,
        dataStore: DataStore<Preferences>
    ) : List<String> = runBlocking {
        var extraCategories = listOf<String>()
        val dataList = dataStore.data.first()[stringPreferencesKey(categoryPreferencesKey)]?.replace("null\n", "")?.split("\n")
        if (!dataList.isNullOrEmpty()) { extraCategories = dataList }

        when(type) {
            true -> {
                when (locale.language) {
                    "ru" -> incomeCategoriesRU + extraCategories
                    else -> incomeCategories + extraCategories
                }
            }
            false -> {
                when (locale.language) {
                    "ru" -> categoriesRU + extraCategories
                    else -> categories + extraCategories
                }
            }
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
    category_picker: MutableState<String>,
    type: Boolean,
    dataStore: DataStore<Preferences>
) {
    val c = remember { mutableStateOf("") }
    val categories = Categories.getAll(Categories.currentLocation(), type, dataStore)
    if (show.value){
        Dialog(
            onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Column(
                    modifier = Modifier.background(MaterialTheme.colors.secondary)
                ) {
                    for (category in categories) {
                        TextButton(
                            onClick = { category_picker.value = category; onDismiss() }) {
                            Text(text = category)
                        }
                    }
                    TextField(
                        label = { Text(text = "Custom") },
                        value = c.value,
                        onValueChange = { c.value = it },
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onDone = { category_picker.value = c.value; onDismiss() }
                        ),
                        modifier = Modifier
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
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