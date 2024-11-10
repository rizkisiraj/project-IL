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
import com.example.resikelapp.ui.screens.community.Community
import com.example.resikelapp.ui.screens.community.CommunityDetail

@Composable
fun NavigationGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "beranda") {
        berandaGraph(navController)
        composable(route = Screen.Map.route!!) {
            TODO()
        }
        composable(route = Screen.Profile.route!!) {
            TODO()
        }
        composable(route = Screen.Community.route!!) {
            Community(
                navigateToDetail = { communityId ->
                    navController.navigate(Screen.DetailCommunity.createRoute(communityId))
                }
            )
        }
        composable(
            route = Screen.DetailCommunity.route!!,
            arguments = listOf(navArgument("communityId") {type = NavType.LongType})
        ) {
            val id = it.arguments?.getLong("communityId") ?: -1L
            CommunityDetail(
                communityId = id,
                navigateBack = {
                    navController.navigateUp()
                },
            )
        }
        composable(
            route = Screen.Cart.route!!
        ) {
            KalkulasiScreen()
        }

    }
}