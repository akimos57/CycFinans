package ru.cyclone.cycfinans.presentation.screens.target

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.theme.gold

@Composable
fun AddTarget(navController: NavHostController) {
    var i = ""
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colors.background)
                    .clickable { navController.navigate(Screens.TargetScreen.rout) },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }
            Box(

                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "back",
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }
        }
        TextField(
            value = "",
            onValueChange = {i = it},
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = gold,
                disabledLabelColor = MaterialTheme.colors.secondary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.background
            ),
            textStyle = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Medium),
            placeholder = {
                Text(
                    text = "Название цели",
                    fontSize = 22.sp
                )
            }
        )
        TextField(
            value = "",
            onValueChange = { i = it },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = gold,
                disabledLabelColor = MaterialTheme.colors.secondary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.background
            ),
            textStyle = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Medium),
            placeholder = {
                Text(
                    text = "Нужно собрать",
                    fontSize = 22.sp
                )
            }
        )
    }
}