package com.example.resikelapp.data.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.BerandaScreen
import com.example.resikelapp.ui.screens.news.DetailNewsScreen
import com.example.resikelapp.ui.screens.news.NewsScreen

fun NavGraphBuilder.berandaGraph(navController: NavController) {
    navigation(startDestination = Screen.Home.route!!, route = "beranda") {
        composable(route = Screen.Home.route) {
            BerandaScreen(navController)
        }
        composable(route = Screen.News.route!!) {
            NewsScreen(navController)
        }
        composable(route = Screen.DetailNews.route!!) {
            DetailNewsScreen()
        }
    }
}