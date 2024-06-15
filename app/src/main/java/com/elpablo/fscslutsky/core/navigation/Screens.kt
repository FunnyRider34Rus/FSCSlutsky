package com.elpablo.fscslutsky.core.navigation

sealed class Screen(val route: String) {
    data object AUTHORIZATION : Screen(route = "authorization")
    data object DASHBOARD : Screen(route = "dashboard")
    data object SETTINGS : Screen(route = "settings")
}