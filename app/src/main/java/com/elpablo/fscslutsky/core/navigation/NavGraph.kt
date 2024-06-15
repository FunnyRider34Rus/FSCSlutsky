package com.elpablo.fscslutsky.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    Scaffold { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = Screen.AUTHORIZATION.route) {

            }
            composable(route = Screen.DASHBOARD.route) {

            }
            composable(route = Screen.SETTINGS.route) {

            }
        }
    }
}