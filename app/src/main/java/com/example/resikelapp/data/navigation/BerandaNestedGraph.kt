package com.example.resikelapp.data.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.BerandaScreen
import com.example.resikelapp.ui.screens.community.CommunityDetail
import com.example.resikelapp.ui.screens.news.DetailNewsScreen
import com.example.resikelapp.ui.screens.news.NewsScreen
import com.example.resikelapp.ui.screens.news.NewsViewModel

fun NavGraphBuilder.berandaGraph(navController: NavController) {
    navigation(startDestination = Screen.Home.route!!, route = "beranda") {
        composable(route = Screen.Home.route) {
            BerandaScreen(navController)
        }
        composable(route = Screen.News.route!!) {
            NewsScreen(navController)
        }
        composable(
            route = Screen.DetailNews.route!!,
            arguments = listOf(navArgument("newsId") {type = NavType.StringType})
        ) {
            val id = it.arguments?.getString("newsId").toString()
            DetailNewsScreen(
                newsId = id
            )
        }
    }
}