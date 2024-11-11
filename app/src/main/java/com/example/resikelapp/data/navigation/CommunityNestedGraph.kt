package com.example.resikelapp.data.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.community.Community
import com.example.resikelapp.ui.screens.community.CommunityDetail

fun NavGraphBuilder.communityGraph(navController: NavController) {
    navigation(startDestination = Screen.Community.route!!, route = "community__graph") {
        composable(route = Screen.Community.route) {
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
    }
}