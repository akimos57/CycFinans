package ru.cyclone.cycfinans.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.components.TargetBox
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TargetScreen(navController: NavHostController) {
    Scaffold(
        floatingActionButton = {FloatingActionButton(
            onClick = { /*TODO*/ },
            backgroundColor = Color(0xFFFFD700)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "add",
                modifier = Modifier
                    .height(33.dp)
                    .width(33.dp)
            )
        }}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 26.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Box(
//                    modifier = Modifier
//                        .width(48.dp)
//                        .height(48.dp)
//                        .clip(RoundedCornerShape(24.dp))
//                        .clickable { navController.navigate(Screens.MainScreen.rout) },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.ArrowBack,
//                        contentDescription = "back",
//                        modifier = Modifier
//                            .height(25.dp)
//                            .width(25.dp)
//                    )
//                }
                Text(
                    text = "Цели",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 16.dp)
                )
            }

            TargetBox(
                modifier = Modifier
                    .clickable { navController.navigate(AdditionalScreens.TargetDetailsScreen.rout) }
            )
            TargetBox(
                modifier = Modifier
                    .clickable { navController.navigate(AdditionalScreens.TargetDetailsScreen.rout) }
            )
        }
    }
}