package com.elpablo.fscslutsky.core.navigation

sealed class Screen(val route: String) {
    data object AUTH : Screen(route = "auth")
    data object WALL : Screen(route = "wall")
    data object DASHBOARDLIST : Screen(route = "dashboard_list")
    data object DASHBOARDDETAIL : Screen(route = "dashboard_detail")
    data object MATCHES : Screen (route = "matches")
    data object SHOP : Screen(route = "shop")
    data object PROFILE : Screen(route = "settings")
}