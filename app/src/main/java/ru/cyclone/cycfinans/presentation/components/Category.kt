package ru.cyclone.cycfinans.presentation.components

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.flow.first
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import java.util.*
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import ru.cyclone.cycfinans.presentation.ui.theme.fab1

data class Category(
    @Json(name = "category_table")
    val name: String,
    val type: Boolean,
    val language: String,
    var limit: Int = 0
)

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
        "Транспорт",
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
        "Зарплата",
        "Бизнес",
        "Недвижимость",
        "Акции",
        "Другое"
    )

    fun getAll(
        locale: Locale,
        type: Boolean,
    ) : List<String> {
        var extraCategories = PreferencesController().fileNameList.map { it }
//        val dataList = dataStore.data.first()[stringPreferencesKey(categoryPreferencesKey)]?.replace("null\n", "")?.split("\n")?.distinct()
//        if (!dataList.isNullOrEmpty()) { extraCategories = dataList }

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
    type: Boolean,
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
//                        TextButton(
//                            onClick = { category_picker.value = category; onDismiss() }) {
//                            Text(text = category)
//                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clickable { category_picker.value = category; onDismiss() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 16.dp),
                                text = category,
                                fontSize = 18.sp
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        ) {
                            TextField(
                                label = {
                                    Text(
                                        text = "Новая категория",
                                        color = fab1
                                    )
                                        },
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
                                textStyle = TextStyle(fontSize = 18.sp),
                                colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = fab1,
                                    disabledLabelColor = MaterialTheme.colors.secondary,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    backgroundColor = MaterialTheme.colors.secondary
                                ),
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .border(
                                        width = 1.3.dp,
                                        color = fab1,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .background(MaterialTheme.colors.secondary)
                                    .clickable { category_picker.value = c.value; onDismiss() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "add"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}