package com.example.resikelapp.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.resikelapp.data.model.Screen.*
import com.example.resikelapp.data.model.Screen.Map

sealed class Screen(
    val route: String?,
    val title: String?,
    val icon: ImageVector?
) {
    object Home: Screen("home", "Home", Icons.Outlined.Home)
    object Map: Screen("map", "Map", Icons.Outlined.Map)
    object Cart: Screen("cart", null, Icons.Filled.ShoppingCart)
    object Community: Screen("community", "Commun", Icons.Outlined.People)
    object DetailCommunity: Screen("community/{communityId}", "Community", null){
        fun createRoute(communityId: String) = "community/$communityId"
    }
    object Profile: Screen("profile", "Profile", Icons.Outlined.Person)
    object News: Screen("news", "News", null)
    object Disimpan: Screen("disimpan", "Disimpan", null)
    object Privacy: Screen("privacy", "Privasi dan Keamanan", null)
    object UbahSandi: Screen("ubahsandi", "Ubah Sandi", null)
    object UbahAkun: Screen("ubahakun", "Ubah Akun", null)
    object DetailNews: Screen("news/{newsId}", "Detail News", null){
        fun createRoute(newsId: String) = "news/$newsId"
    }
}

val screenList = listOf(
    Screen.Home,
    Screen.Map,
    Screen.Community,
    Screen.Profile
)

val allScreens = listOf(
    Home,
    Map,
    Cart,
    Community,
    DetailCommunity,
    Profile,
    News,
    DetailNews
)
