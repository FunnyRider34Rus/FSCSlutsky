package com.elpablo.fscslutsky.core.navigation

sealed class Screen(val route: String) {
    data object DASHBOARD : Screen(route = "dashboard")
    data object MATCHES : Screen (route = "matches")
    data object SHOP : Screen(route = "shop")
    data object PROFILE : Screen(route = "settings")
}