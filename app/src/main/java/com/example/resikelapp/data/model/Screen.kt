package com.example.resikelapp.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String?,
    val title: String?,
    val icon: ImageVector?
) {
    object Home: Screen("home", "Home", Icons.Outlined.Home)
    object Map: Screen("map", "Map", Icons.Outlined.Map)
    object Cart: Screen("cart", null, Icons.Filled.ShoppingCart)
    object Community: Screen("community", "Commun", Icons.Outlined.People)
    object Profile: Screen("profile", "Profile", Icons.Outlined.Person)
}

val screenList = listOf(
    Screen.Home,
    Screen.Map,
    Screen.Community,
    Screen.Profile
)
