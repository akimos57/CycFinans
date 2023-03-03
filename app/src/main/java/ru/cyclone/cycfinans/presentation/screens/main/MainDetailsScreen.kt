package ru.cyclone.cycfinans.presentation.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.components.PromotionBox
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycfinans.presentation.ui.theme.fab2

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainDetailsScreen(navController: NavHostController) {
//    val promotions: List<Promotion> = listOf(
//        Promotion(
//            id = 0,
//            type = true,
//            category = "House",
//            colorCategory = Color.Red.toArgb(),
//            price = 2000f
//        )
//    )

    val viewModel = hiltViewModel<MainDetailsVM>()
    val promotions = viewModel.promotions.observeAsState(listOf()).value
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { navController.navigate(Screens.MainScreen.rout) },
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

            Text(
                text = "1 января",
                fontSize = 28.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }


    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(Screens.AddPromotionScreen.rout) },
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                    backgroundColor = fab1
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "add",
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Доходы",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light
                    )
                }
//                promotions.forEach { promotion ->
//                    PromotionBox(
//                        price = promotion.price,
//                        category = promotion.category,
//                        colorCategory = Color(promotion.colorCategory),
//                        modifier = Modifier
//                    )
//                }
            }
        }
        Scaffold(
            modifier = Modifier,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(Screens.AddPromotionScreen.rout) },
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                    backgroundColor = fab2
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "add",
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Расходы",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light
                )
            }
                promotions.forEach { promotion ->
                    PromotionBox(
                        price = promotion.price,
                        category = promotion.category,
                        colorCategory = Color(promotion.colorCategory),
                        modifier = Modifier
                            .clickable { navController.navigate(Screens.AddPromotionScreen.rout
//                                    + "/${promotion.id}"
                            )}
                    )
                }

            }
        }

    }
    }
}
