package com.example.resikelapp.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.KalkulasiScreen
import com.example.resikelapp.ui.screens.auth.LoginScreen
import com.example.resikelapp.ui.screens.MapScreen
import com.example.resikelapp.ui.screens.auth.RegisterScreen

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "register") {
        berandaGraph(navController)
        kalkulasiGraph(navController)
        composable(route = Screen.Map.route!!) {
            MapScreen()
        }

        composable(route = Screen.Profile.route!!) {
            TODO()
        }
//        composable(
//            route = Screen.Cart.route!!
//        ) {
//            KalkulasiScreen(navController = navController)
//        }
        communityGraph(navController)
        profileGraph(navController)
        composable(
            route = "login"
        ) {
            LoginScreen(
                onLogin = { _, _ -> navController.navigate("beranda")},
                onNavigateToForgotPassword = {},
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable(
            route = "register"
        ) {
            RegisterScreen(
                onRegister = { _, _, _, _ -> },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
    }
}