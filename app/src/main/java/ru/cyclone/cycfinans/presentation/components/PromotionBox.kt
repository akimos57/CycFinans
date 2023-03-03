package ru.cyclone.cycfinans.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PromotionBox(
    price: Int,
    category: String,
    colorCategory: Color,
    modifier: Modifier
) {
//    var price by rememberSaveable { mutableStateOf("") }
    Column(modifier = modifier) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "+ $price ₽",
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )
                }

//            TextField(
//                value = price,
//                onValueChange = { price = it },
//                modifier = Modifier
//                    .fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                colors = TextFieldDefaults.textFieldColors(
//                    backgroundColor = Color.White,
//                    textColor = colorCategory,
//                    cursorColor = colorCategory
//                )
//            )
                TextButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = colorCategory
                    )
                ) {
                    Text(text = category)
                }
            }

        }
    }
}