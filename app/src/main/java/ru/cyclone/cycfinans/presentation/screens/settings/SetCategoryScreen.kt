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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.cyclone.cycnote.R

@Composable
fun SetCategoryScreen(
    navController: NavController,
    dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
) {
    val vm = hiltViewModel<SetCategoryScreenVM>()
    vm.dataStore = dataStore
    val limits = vm.categories.observeAsState()
    val focusRequester = remember { FocusRequester() }
    val focus = LocalFocusManager.current

    val coroutine = rememberCoroutineScope()
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
                            coroutine.launch {
                                dataStore.edit { mutablePreferences ->
                                    mutablePreferences[stringPreferencesKey(category)] = str
                                }
                            }
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