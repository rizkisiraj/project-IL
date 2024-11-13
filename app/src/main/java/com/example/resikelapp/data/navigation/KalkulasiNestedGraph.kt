package com.example.resikelapp.data.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.KalkulasiScreen

fun NavGraphBuilder.kalkulasiGraph(navController: NavController) {
    navigation(startDestination = Screen.Cart.route!!, route = "kalkulasi_graph") {
        composable(route = Screen.Cart.route) {
            KalkulasiScreen(onBackClick = { navController.navigateUp() })
        }
    }
}
