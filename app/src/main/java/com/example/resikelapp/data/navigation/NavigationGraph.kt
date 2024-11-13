package com.example.resikelapp.data.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.BerandaScreen
import com.example.resikelapp.ui.screens.KalkulasiScreen
import com.example.resikelapp.ui.screens.LoginScreen
import com.example.resikelapp.ui.screens.RegisterScreen
import com.example.resikelapp.ui.screens.community.Community
import com.example.resikelapp.ui.screens.community.CommunityDetail

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "register") {
        berandaGraph(navController)
        kalkulasiGraph(navController)
        composable(route = Screen.Map.route!!) {
            TODO()
        }
        composable(route = Screen.Profile.route!!) {
            TODO()
        }
        composable(
            route = Screen.Cart.route!!
        ) {
            KalkulasiScreen()
        }
        communityGraph(navController)
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