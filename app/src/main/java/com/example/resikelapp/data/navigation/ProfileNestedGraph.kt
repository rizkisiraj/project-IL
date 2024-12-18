package com.example.resikelapp.data.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.ui.screens.aktivitas.AktivitasScreen
import com.example.resikelapp.ui.screens.disimpan.DisimpanScreen
import com.example.resikelapp.ui.screens.privacy.PrivacyScreen
import com.example.resikelapp.ui.screens.profile.ProfileScreen
import com.example.resikelapp.ui.screens.ubahakun.UbahAkun
import com.example.resikelapp.ui.screens.ubahsandi.UbahSandi

fun NavGraphBuilder.profileGraph(navController: NavController) {
    navigation(startDestination = Screen.Profile.route!!, route = "profile_graph") {
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(route = Screen.Disimpan.route!!) {
            DisimpanScreen()
        }
        composable(route = Screen.Aktivitas.route!!) {
            AktivitasScreen()
        }
        composable(route = Screen.Privacy.route!!) {
            PrivacyScreen(navController)
        }
        composable(route = Screen.UbahSandi.route!!) {
            UbahSandi(navController)
        }
        composable(route = Screen.UbahAkun.route!!) {
            UbahAkun(navController)
        }
    }
}