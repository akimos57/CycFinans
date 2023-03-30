package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.theme.fab1

@Composable
fun BottomNavigationBar(
    navController: NavController,
    show: Boolean
) {
    if (show) {
        val listItems = listOf(
            Screens.MainScreen,
            Screens.CalendarScreen,
            Screens.StatisticsScreen,
            Screens.SettingsScreen
        )

        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            listItems.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.rout,
                    onClick = {
                        navController.navigate(item.rout) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = false
                            }
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "icon"
                    )
                    },
//                    label = {
//                        Text(
//                            text = stringResource(id = item.titleId)
//                        )
//                    },
                    selectedContentColor = fab1,
                    unselectedContentColor = MaterialTheme.colors.primaryVariant
                )

            }
        }
    }
}