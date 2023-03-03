package ru.cyclone.cycfinans.presentation.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.theme.fab1

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val listItems = listOf(
        Screens.MainScreen,
        Screens.TargetScreen,
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
                          navController.navigate(item.rout)
                },
                icon = { Icon(
                    painter = painterResource(id = item.iconId),
                    contentDescription = "icon"
                )
                },
                label = { 
                    Text(text = item.title)
                },
                selectedContentColor = fab1,
                unselectedContentColor = Color.Gray
            )

        }
    }
}