package ru.cyclone.cycfinans.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.cyclone.cycfinans.presentation.components.DayBox
import ru.cyclone.cycfinans.presentation.components.MainBox
import ru.cyclone.cycfinans.presentation.components.WidgetMain
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.theme.CycFinansTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TextButton(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                )
            ) {

                    Text(
                        text = "Январь",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Light
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "arrow",
                        modifier = Modifier
                            .height(33.dp)
                            .width(33.dp)
                    )
                }

            MainBox(
                modifier = Modifier
                    .clickable { navController.navigate(Screens.StatisticsScreen.rout) }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(bottom = 12.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "new",
                    )
                }

                WidgetMain()
                WidgetMain()
                WidgetMain()
            }

            DayBox(
                modifier = Modifier
                .clickable { navController.navigate(AdditionalScreens.MainDetailsScreen.rout) }
            )


            }
        }
    }




@Preview(showBackground = true)
@Composable
fun previewMainScreen() {
    CycFinansTheme {
        MainScreen(navController = rememberNavController())
    }
}