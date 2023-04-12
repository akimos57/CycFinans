package ru.cyclone.cycfinans.presentation.ui.components

import android.view.KeyEvent
import androidx.compose.foundation.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.cyclone.cycfinans.domain.model.Categories
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycnote.R

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
            onDismissRequest = onDismiss
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .background(MaterialTheme.colors.secondary)
                ) {
//                    for (category in categories) {
////                        TextButton(
////                            onClick = { category_picker.value = category; onDismiss() }) {
////                            Text(text = category)
////                        }
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(40.dp)
//                                .clickable { category_picker.value = category; onDismiss() },
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                modifier = Modifier
//                                    .padding(start = 16.dp),
//                                text = category,
//                                fontSize = 18.sp
//                            )
//                        }
//                    }
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
                                        text = stringResource(id = R.string.new_category),
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
                }
            }
        }
    }
}