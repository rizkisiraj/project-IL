package com.example.resikelapp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.resikelapp.data.model.screenList
import com.example.resikelapp.ui.theme.GreenBase
import com.example.resikelapp.ui.theme.GreenSecondary

@Composable
fun BottomNav(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .height(80.dp),
        tonalElevation = 4.dp,
        containerColor = Color.White
    ) {
        screenList.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(screen.title!!)
                },
                icon = { Icon(screen.icon!!, contentDescription = screen.title!!) },
                selected = currentRoute?.hierarchy?.any {
                    it.route?.contains(screen.route ?: "") == true || (it.route?.contains("news") == true && screen.route == "home")
                } == true,
                onClick = {  navController.navigate(screen.route!!) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = GreenBase,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = GreenBase,
                    unselectedTextColor = Color.Gray,
                    disabledIconColor = Color.White,
                    disabledTextColor = Color.White,
                    selectedIndicatorColor = GreenSecondary
                )
            )
            if(screen.route == "map") {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}