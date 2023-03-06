package ru.cyclone.cycfinans.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PromotionBox(
    price: Int,
    category: String,
    colorCategory: Color,
    modifier: Modifier
) {
//    var price by rememberSaveable { mutableStateOf("") }
    Column(modifier = Modifier) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.secondary)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "- $price ₽",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 20.dp)

                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = category,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
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

            }

        }
    }
}