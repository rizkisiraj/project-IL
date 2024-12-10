package com.example.resikelapp.data.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
        composable(
            route = Screen.News.route!!,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) {
            NewsScreen(navController)
        }
        composable(
            route = Screen.DetailNews.route!!,
            arguments = listOf(navArgument("newsId") {type = NavType.StringType}),
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) {
            val id = it.arguments?.getString("newsId").toString()
            DetailNewsScreen(
                newsId = id
            )
        }
    }
}