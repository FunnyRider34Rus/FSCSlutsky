package com.elpablo.fscslutsky.core.navigation

sealed class Graph(val route: String) {
    data object AUTHORIZATION : Graph(route = "onboard")
    data object MAIN : Graph(route =  "main")
}

sealed class Screen(val route: String) {
    data object WELCOME : Screen(route = "welcome")
    data object PHONE : Screen(route = "phone")
    data object SMS : Screen(route = "sms")
    data object DASHBOARD : Screen(route = "dashboard")
    data object SETTINGS : Screen(route = "settings")
}