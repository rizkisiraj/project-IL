package com.example.resikelapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.data.model.allScreens
import com.example.resikelapp.data.model.screenList
import com.example.resikelapp.data.navigation.NavigationGraph
import com.example.resikelapp.ui.components.BottomNav
import com.example.resikelapp.ui.theme.GreenBase
import com.example.resikelapp.ui.theme.GreenSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val bottomBarDestination = currentDestination?.route == Screen.Cart.route || currentDestination?.route == "login" || currentDestination?.route == "register" || currentDestination?.route == Screen.DetailNews.route || currentDestination?.route == Screen.DetailCommunity.route || currentDestination?.route == "success"
            if(!bottomBarDestination) {
                BottomNav(navController)
            }
        },
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val bottomBarDestination = screenList.any { it.route == currentDestination?.route } || currentDestination?.route == Screen.Cart.route || currentDestination?.route == "login" || currentDestination?.route == "register" || currentDestination?.route == "community" || currentDestination?.route == "success"

            if(!bottomBarDestination) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = GreenSecondary,
                        titleContentColor = GreenBase,
                    ),
                    title = {
                        if(currentDestination?.route == "news") {
                            Text("Berita Terkini")
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back Button"
                            )
                        }
                    },
                )
            }
        },
        floatingActionButton = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val bottomBarDestination = currentDestination?.route == Screen.Cart.route || currentDestination?.route == "login" || currentDestination?.route == "register" || currentDestination?.route == Screen.DetailNews.route || currentDestination?.route == Screen.DetailCommunity.route || currentDestination?.route == "success"
            if(!bottomBarDestination) {
                FloatingActionButton(
                    onClick = {
                        Screen.Cart.route?.let {
                            navController.navigate(it) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        Screen.Cart.route?.let { navController.navigate(it) }
                    },
                    containerColor = GreenBase,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(80.dp)
                        .offset(y = 60.dp)
                ) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(45.dp))
                }
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPadding ->
            NavigationGraph(navController)
        }
    )
}
