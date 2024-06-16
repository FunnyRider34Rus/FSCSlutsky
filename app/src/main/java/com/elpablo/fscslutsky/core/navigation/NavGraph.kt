package com.elpablo.fscslutsky.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.elpablo.fscslutsky.ui.welcome.WelcomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    Scaffold { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            navigation(startDestination = Screen.WELCOME.route, route = Graph.AUTHORIZATION.route) {
                composable(route = Screen.WELCOME.route) {
                    WelcomeScreen(
                        modifier = modifier,
                        navigateToNextScreen = {  }
                    )
                }
                composable(route = Screen.PHONE.route) {

                }
                composable(route = Screen.SMS.route) {

                }
            }
            navigation(startDestination = Screen.DASHBOARD.route, route = Graph.MAIN.route) {
                composable(route = Screen.DASHBOARD.route) {

                }
                composable(route = Screen.SETTINGS.route) {

                }
            }
        }
    }
}