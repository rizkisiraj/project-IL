package com.example.resikelapp.data.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.KalkulasiScreen

fun NavGraphBuilder.kalkulasiGraph(navController: NavController) {
    navigation(startDestination = Screen.Cart.route!!, route = "kalkulasi_graph") {
        composable(
            route = Screen.Cart.route,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(700)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
                )
            }
        ) {
            KalkulasiScreen(onBackClick = { navController.navigate("beranda") {
                popUpTo("kalkulasi_graph") {
                    inclusive = true
                }
            } })
        }
    }
}
