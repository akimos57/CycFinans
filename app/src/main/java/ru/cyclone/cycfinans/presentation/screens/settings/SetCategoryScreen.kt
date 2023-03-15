package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.beust.klaxon.Klaxon
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import ru.cyclone.cycfinans.presentation.components.Category
import ru.cyclone.cycnote.R
import java.util.*

@Composable
fun SetCategoryScreen() {
    val vm = hiltViewModel<SetCategoryScreenVM>()
    val limits = vm.categories.observeAsState()
    val focusRequester = remember { FocusRequester() }
    val focus = LocalFocusManager.current

    val preferencesController = PreferencesController()
    Scaffold(
        topBar = {
            val appName = stringResource(id = R.string.app_name)
            TopAppBar(
                title = { Text(text = appName) },
                backgroundColor = MaterialTheme.colors.background
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colors.background)
        ) {
            limits.value?.forEach { (category, limit) ->
                var str by remember { mutableStateOf(limit) }
                TextField(
                    label = { Text( text = category ) },
                    value = str,
                    onValueChange = { str = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.background
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            val resultString = Klaxon().toJsonString(Category(category, false, Locale.getDefault().language))
                            val index = preferencesController.fileNameList.indexOf(resultString)
                            preferencesController.fileNameList[index] =
                                Klaxon().toJsonString(Category(category, false, Locale.getDefault().language, limit = str.toInt()))
                            preferencesController.saveLists()
                            focus.clearFocus()
                        }
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth()
                )
            }
        }
    }
}