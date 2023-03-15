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
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import java.util.*

data class Category(
    @Json(name = "category_table")
    val name: String,
    val type: Boolean,
    val language: String,
    var limit: Int = 0
)

object Categories {
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
        type: Boolean
    ) : List<String> {
        val extraCategories = PreferencesController().fileNameList.map { it }

        val categoryList = getCategoryByLocationAndType(
            extraCategories.mapNotNull { Klaxon().parse<Category>(it) },
            language = locale.language,
            type = type
        )

        return when(type) {
            true -> {
                when (locale.language) {
                    "ru" -> incomeCategoriesRU + categoryList
                    else -> incomeCategories + categoryList
                }
            }
            false -> {
                when (locale.language) {
                    "ru" -> categoriesRU + categoryList
                    else -> categories + categoryList
                }
            }
        }
    }

    // For future usage in translation
    private fun getCategoryByLocationAndType(
        categoryList: List<Category>,
        language: String,
        type: Boolean
    ): List<String>  {
        return categoryList.filter { it.type == type }.map { category ->
            val categoryName = category.name
            if (category.language != language) {
                // ToDo translate here
                categoryName
            } else categoryName
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
    type: Boolean
) {
    val c = remember { mutableStateOf("") }
    val categories = Categories.getAll(Categories.currentLocation(), type)
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