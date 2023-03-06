package ru.cyclone.cycfinans.presentation.screens.main

import android.os.Build
import android.text.format.Time
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import java.sql.Timestamp
import java.time.Clock
import java.time.LocalTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddPromotion(
    navController: NavHostController,
    type: Boolean
//    id: String?
) {
//        Log.d("checkData", "id: $${id}")
        val viewModel = hiltViewModel<AddPromotionVM>()
        var price by rememberSaveable { mutableStateOf("") }
        var category by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(horizontal = 36.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.secondary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.secondary,
//                        textColor = colorCategory,
//                        cursorColor = colorCategory
                    )
                )
                TextField(
                    value = category,
                    onValueChange = { category = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.secondary,
//                        textColor = colorCategory,
//                        cursorColor = colorCategory
                    )
                )
                TextButton(
                    onClick = {
                        val price = price.toInt()
                        val color = Color.White.toArgb()
                        val category = category
                        val currentTime = java.sql.Time(System.currentTimeMillis())
                        viewModel.addPromotion(
                            Promotion(
                                type = true,
                                category = category,
                                colorCategory = color,
                                price = price,
                                time = currentTime
                            )
                        ) {
//                            navController.navigate(Screens.MainDetailsScreen.rout)
                        }
                        navController.navigate(AdditionalScreens.MainDetailsScreen.rout)
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
//                        contentColor = colorCategory
                    )
                ) {
                    Text(text = "Категория")
                }
            }

        }
    }

    }
