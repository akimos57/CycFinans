package ru.cyclone.cycfinans.presentation.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.components.*
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycfinans.presentation.ui.theme.fab2
import ru.cyclone.cycnote.R
import java.sql.Time
import java.time.Month
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainDetailsScreen(
    navController: NavHostController,
    day: String?,
    month: String?,
    year: String?,
    onReturned: MutableState<() -> Unit>
) {
    val date = Calendar.getInstance()
    date.set(year!!.toInt(), month!!.toInt() - 1, day!!.toInt())

    val viewModel = hiltViewModel<MainDetailsScreenVM>()
    viewModel.date = date
    val promotions = viewModel.promotions.observeAsState(listOf()).value

    onReturned.value = {
        viewModel.updateNotes()
    }

    var type = false

    Box {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable {
                            navController.navigate(Screens.MainScreen.rout) {
                                popUpTo(Screens.MainScreen.rout) {
                                    inclusive = true
                                }
                            }
                        },
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
                    text = "$day ${ Month.of(month.toInt()).getDisplayName(TextStyle.FULL, Locale.getDefault()) }, $year",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }
            NotesInDetails()
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val s = remember { mutableStateOf(false) }
                Scaffold(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(),
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { s.value = true; type = true },
                            modifier = Modifier,
                            backgroundColor = fab1
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "add",
                                modifier = Modifier
                                    .height(33.dp)
                                    .width(33.dp)
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.income),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                        promotions.filter { it.type }.forEach { promotion ->
                            val showDialog = remember { mutableStateOf(false) }
                            val showDialog1 = remember { mutableStateOf(false) }
                            EditPromotion(showDialog.value, viewModel, onDismiss = { showDialog.value = false }, promotion, promotion.time.time)
                            if (showDialog1.value) {
                                Dialog(
                                    onDismissRequest = { showDialog1.value = false }) {
                                    Box(
                                        modifier = Modifier
                                            .height(120.dp)
                                            .width(250.dp)
                                            .clip(RoundedCornerShape(24.dp))
                                            .background(MaterialTheme.colors.secondary)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 24.dp),
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "Удалить?",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Medium,
                                            )
                                        }

                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            verticalArrangement = Arrangement.Bottom
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                OutlinedButton(
                                                    modifier = Modifier
                                                        .padding(end = 20.dp),
                                                    shape = CircleShape,
                                                    border = BorderStroke(1.dp, color = Color.Transparent),
                                                    colors = ButtonDefaults.outlinedButtonColors(
                                                        backgroundColor = MaterialTheme.colors.secondary,
                                                        contentColor = MaterialTheme.colors.primaryVariant
                                                    ),
                                                    onClick = { showDialog1.value = false }) {
                                                    Text(
                                                        text = "Отмена",
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                }
                                                OutlinedButton(
                                                    modifier = Modifier
                                                        .padding(end = 20.dp),
                                                    shape = CircleShape,
                                                    border = BorderStroke(1.dp, color = Color.Transparent),
                                                    colors = ButtonDefaults.outlinedButtonColors(
                                                        backgroundColor = fab2,
                                                        contentColor = MaterialTheme.colors.primaryVariant
                                                    ),
                                                    onClick = { showDialog1.value = false; viewModel.deletePromotion(promotion = promotion) }) {
                                                    Text(
                                                        text = "Удалить",
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Medium,
                                                    )
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                            PromotionBox(
                                promotion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .combinedClickable(
                                        onClick = { showDialog.value = true },
                                        onLongClick = { showDialog1.value = true }
                                    )
                            )
                        }
                    }
                }
                Scaffold(
                    modifier = Modifier,
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { s.value = true; type = false },
                            modifier = Modifier,
                            backgroundColor = fab2
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "add",
                                modifier = Modifier
                                    .size(33.dp)
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) { paddingValues ->

                    EditPromotion(
                        show = s.value, vm = viewModel, onDismiss = { s.value = false }, promotion = Promotion(
                            time = Time(date.timeInMillis),
                            type = type,
                            category = "",
                            colorCategory = 0,
                            price = 0
                        ), date = date.timeInMillis
                    )
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.expenses),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                        promotions.filter { !it.type }.forEach { promotion ->
                            val showDialog = remember { mutableStateOf(false) }
                            val showDialog1 = remember { mutableStateOf(false) }
                            EditPromotion(
                                showDialog.value,
                                viewModel,
                                onDismiss = { showDialog.value = false },
                                promotion,
                                promotion.time.time
                            )
                            if (showDialog1.value) {
                                Dialog(
                                    onDismissRequest = { showDialog1.value = false }) {
                                    Box(
                                        modifier = Modifier
                                            .height(120.dp)
                                            .width(250.dp)
                                            .clip(RoundedCornerShape(24.dp))
                                            .background(MaterialTheme.colors.secondary)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 24.dp),
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.delete)+"?",
                                                fontSize = 20.sp,
                                                fontWeight = FontWeight.Medium,
                                            )
                                        }

                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            verticalArrangement = Arrangement.Bottom
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.End
                                            ) {
                                                OutlinedButton(
                                                    modifier = Modifier
                                                        .padding(end = 20.dp),
                                                    shape = CircleShape,
                                                    border = BorderStroke(1.dp, color = Color.Transparent),
                                                    colors = ButtonDefaults.outlinedButtonColors(
                                                        backgroundColor = MaterialTheme.colors.secondary,
                                                        contentColor = MaterialTheme.colors.primaryVariant
                                                    ),
                                                    onClick = { showDialog1.value = false }) {
                                                    Text(
                                                        text = stringResource(id = R.string.cancel),
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                }
                                                OutlinedButton(
                                                    modifier = Modifier
                                                        .padding(end = 20.dp),
                                                    shape = CircleShape,
                                                    border = BorderStroke(1.dp, color = Color.Transparent),
                                                    colors = ButtonDefaults.outlinedButtonColors(
                                                        backgroundColor = fab2,
                                                        contentColor = MaterialTheme.colors.primaryVariant
                                                    ),
                                                    onClick = { showDialog1.value = false; viewModel.deletePromotion(promotion = promotion) }) {
                                                    Text(
                                                        text = stringResource(id = R.string.delete),
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Medium,
                                                    )
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                            PromotionBox(
                                promotion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .combinedClickable(
                                        onClick = { showDialog.value = true },
                                        onLongClick = { showDialog1.value = true }
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
    BackHandler {
        navController.navigate(Screens.MainScreen.rout) {
            popUpTo(Screens.MainScreen.rout) {
                inclusive = true
            }
        }
    }
}