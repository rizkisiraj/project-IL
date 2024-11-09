package com.example.resikelapp.data.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.BerandaScreen
import com.example.resikelapp.ui.screens.community.Community
import com.example.resikelapp.ui.screens.community.CommunityDetailScreen
import com.example.resikelapp.ui.screens.community.CommunityListScreen
import com.example.resikelapp.ui.screens.news.DetailNewsScreen
import com.example.resikelapp.ui.screens.news.NewsScreen

fun NavGraphBuilder.komunitasGraph(navController: NavController) {
    navigation(startDestination = Screen.Community.route!!, route = "komunitas__graph") {
        composable(route = Screen.Community.route) {
            CommunityListScreen(navController = navController)
        }
        composable(route = "communityDetail") {
            CommunityDetailScreen(navController = navController)
        }
    }
}