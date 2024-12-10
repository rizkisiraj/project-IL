package com.example.resikelapp.data.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.data.repository.ResikelRepository
import com.example.resikelapp.ui.screens.KalkulasiScreen
import com.example.resikelapp.ui.screens.SharedViewModel
import com.example.resikelapp.ui.screens.SuccessScreen
import com.example.resikelapp.utils.ViewModelFactory

fun NavGraphBuilder.kalkulasiGraph(navController: NavController) {
    val sharedViewModel: SharedViewModel = SharedViewModel()

    navigation(startDestination = Screen.Cart.route!!, route = "kalkulasi_graph") {
        composable(
            route = Screen.Cart.route,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(700)
                )
            },
            exitTransition = {
                val currentBackStackEntry = navController.currentBackStackEntry
                val destinationRoute = currentBackStackEntry?.destination?.route
                return@composable when (destinationRoute) {
                    "home" -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
                    )
                    else -> null
                }
            }
        ) {
            KalkulasiScreen(
                onBackClick = { navController.navigate("beranda") {
                popUpTo("kalkulasi_graph") {
                    inclusive = true
                }
            } }, navController, sharedViewModel)
        }

        composable(
            route = "success",
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
        ) {
            SuccessScreen(
                onClick = {
                    navController.navigate("beranda") {
                        popUpTo("kalkulasi_graph") {
                            inclusive = true
                        }
                    }
                },
                sharedViewModel = sharedViewModel
            )
        }
    }
}
