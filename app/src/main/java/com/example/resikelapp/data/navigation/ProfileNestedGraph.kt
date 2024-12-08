package com.example.resikelapp.data.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.profile.ProfileScreen

fun NavGraphBuilder.profileGraph(navController: NavController) {
    navigation(startDestination = Screen.Profile.route!!, route = "profile_graph") {
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(route = Screen.Disimpan.route!!) {
            ProfileScreen(navController)
        }
//        composable(
//            route = Screen.DetailCommunity.route!!,
//            arguments = listOf(navArgument("communityId") {type = NavType.StringType})
//        ) {
//            val id = it.arguments?.getString("communityId").toString()
//            CommunityDetail(
//                communityId = id,
//                navigateBack = {
//                    navController.navigateUp()
//                },
//            )
//        }
    }
}