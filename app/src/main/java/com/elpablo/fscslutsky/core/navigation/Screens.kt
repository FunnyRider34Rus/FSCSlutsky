package com.elpablo.fscslutsky.core.navigation

sealed class Screen(val route: String) {
    data object DASHBOARD : Screen(route = "dashboard")
    data object SETTINGS : Screen(route = "settings")
}