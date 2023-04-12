package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.NavHostController
import com.beust.klaxon.Klaxon
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import ru.cyclone.cycfinans.domain.model.Category
import ru.cyclone.cycfinans.presentation.ui.theme.blue
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycfinans.presentation.ui.theme.fab2
import ru.cyclone.cycnote.R
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EditCategoriesListScreen(navController: NavHostController) {
//    HorizontalPager(
//        count = 2
//    ) {
//        when (currentPage) {
//            0 -> {
//                CategoryTypePager(true)
//            }
//            1 -> {
//                CategoryTypePager(false)
//            }
//        }
//    }
    Column {
        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable {
                        navController.popBackStack()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.Center
            ) {
                CategoryTypePager(type = false, navController = navController)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CategoryTypePager(type = true, navController = navController)
            }
        }
    }

}

@Composable
fun CategoryTypePager(type: Boolean, navController: NavHostController) {
    val preferencesController =
        PreferencesController(stringResource(id = R.string.category_table_name))
    val focusRequester = remember { FocusRequester() }
    val focus = LocalFocusManager.current
    var categoryList by remember {
        mutableStateOf(
            preferencesController.fileNameList.map {
                Pair(Klaxon().parse<Category>(it), it)
            }.filter { (it.first?.type == type) and (it.first != null) }
        )
    }
    val colorBorder = if (type) fab1 else fab2
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (type) stringResource(id = R.string.income) else stringResource(id = R.string.expenses),
                fontSize = 24.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(vertical = 20.dp)
            )
        }
//        if (categoryList.isEmpty()) {
//            Text(
//                text = "No extra categories",
//                modifier = Modifier.fillMaxSize()
//            )
//        }

        categoryList.forEach { (category, string) ->
            var limit by remember { mutableStateOf(category!!.limit.toString()) }
            var name by remember { mutableStateOf(category!!.name) }

//            Spacer(modifier = Modifier
//                .fillMaxWidth()
//                .height(1.dp)
//                .background(Color.LightGray)
//                .padding(vertical = 4.dp)
//            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(width = 1.3.dp, color = colorBorder, shape = RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = category!!.name,
                            style = TextStyle(
                                fontSize = 20.sp
                            )
                        )
                    }


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
                            Klaxon().toJsonString(Category(category!!.name, true, Locale.getDefault().language, limit = limit.ifBlank { "0" }.toInt()))
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
                    OutlinedButton(
                        onClick = {
                            preferencesController.fileNameList.remove(string)
                            preferencesController.saveLists()
                            categoryList = preferencesController.fileNameList.map {
                                Pair(Klaxon().parse<Category>(it), it)
                            }.filter { (it.first?.type == type) and (it.first != null) }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 36.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = fab2
                        )
                    ) {
                        Text(text = stringResource(id = R.string.delete))
                    }
                }
            }
        }
    }
}


