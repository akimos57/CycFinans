package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beust.klaxon.Klaxon
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import ru.cyclone.cycfinans.domain.model.Category
import ru.cyclone.cycnote.R
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EditCategoriesListScreen() {
    HorizontalPager(
        count = 2
    ) {
        when (currentPage) {
            0 -> {
                CategoryTypePager(true)
            }
            1 -> {
                CategoryTypePager(false)
            }
        }
    }
}

@Composable
fun CategoryTypePager(type: Boolean) {
    val preferencesController = PreferencesController(stringResource(id = R.string.category_table_name))
    val focusRequester = remember { FocusRequester() }
    val focus = LocalFocusManager.current
    var categoryList by remember {
        mutableStateOf(
            preferencesController.fileNameList.map {
                Pair(Klaxon().parse<Category>(it), it)
            }.filter { (it.first?.type == type) and (it.first != null) }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Text(
            text = if (type) stringResource(id = R.string.income) else stringResource(id = R.string.expenses),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        if (categoryList.isEmpty()) {
            Text(
                text = "No extra categories",
                modifier = Modifier.fillMaxSize()
            )
        }

        categoryList.forEach { (category, string) ->
            var limit by remember { mutableStateOf(category!!.limit.toString()) }
            var name by remember { mutableStateOf(category!!.name) }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
                .padding(vertical = 4.dp)
            )

            Text(
                text = category!!.name,
                style = TextStyle(
                    fontSize = 20.sp
                )
            )

            TextField(
                label = { Text(text = "Name")},
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val index = preferencesController.fileNameList.indexOf(string)
                        if (name.isNotBlank()) {
                            preferencesController.fileNameList[index] =
                                Klaxon().toJsonString(Category(name, true, Locale.getDefault().language, limit = limit.toInt()))
                        } else {
                            preferencesController.fileNameList.remove(string)
                        }
                        preferencesController.saveLists()
                        categoryList = preferencesController.fileNameList.map {
                            Pair(Klaxon().parse<Category>(it), it)
                        }.filter { (it.first?.type == type) and (it.first != null) }
                        focus.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                placeholder = {
                    Text(
                        text = "Введите значение",
                        fontSize = 18.sp
                    )
                },
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                singleLine = true
            )

            TextField(
                label = { Text(text = if (type) "!Limit" else "Limit")},
                value = if (limit == "0") "" else limit,
                onValueChange = { limit = it },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth(),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val index = preferencesController.fileNameList.indexOf(string)
                        preferencesController.fileNameList[index] =
                            Klaxon().toJsonString(Category(category.name, true, Locale.getDefault().language, limit = limit.ifBlank { "0" }.toInt()))
                        preferencesController.saveLists()
                        focus.clearFocus()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                placeholder = {
                    Text(
                        text = "Введите значение",
                        fontSize = 18.sp
                    )
                },
                textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium),
                singleLine = true
            )

            Button(
                onClick = {
                    preferencesController.fileNameList.remove(string)
                    preferencesController.saveLists()
                    categoryList = preferencesController.fileNameList.map {
                        Pair(Klaxon().parse<Category>(it), it)
                    }.filter { (it.first?.type == type) and (it.first != null) }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                )
            ) {
                Text(text = "Delete Category")
            }
        }
    }
}
