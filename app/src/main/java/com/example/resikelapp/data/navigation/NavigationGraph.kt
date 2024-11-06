package com.example.resikelapp.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.BerandaScreen
import com.example.resikelapp.ui.screens.community.Community

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Home.route!!) {
        composable(route = Screen.Home.route) {
            BerandaScreen()
        }
        composable(route = Screen.Map.route!!) {
            TODO()
        }
        composable(route = Screen.Profile.route!!) {
            TODO()
        }
        composable(route = Screen.Community.route!!) {
            Community()
        }

    }
}