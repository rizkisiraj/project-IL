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
import com.example.resikelapp.ui.screens.auth.ChangePasswordScreen

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "register") {
        berandaGraph(navController)
        kalkulasiGraph(navController)
        composable(route = Screen.Map.route!!) {
            MapScreen()
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
                onLogin = {
                    navController.navigate("beranda") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToForgotPassword = {
                    navController.navigate("change_password_screen")
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                },
            )
        }

        composable(
            route = "change_password_screen"
        ) {
            ChangePasswordScreen()
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
