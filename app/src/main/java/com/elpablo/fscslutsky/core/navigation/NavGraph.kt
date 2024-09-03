package com.elpablo.fscslutsky.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elpablo.fscslutsky.ui.dashboard.DashboardScreen
import com.elpablo.fscslutsky.ui.dashboard.DashboardViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    Scaffold { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = Screen.DASHBOARD.route) {
                val viewModel = hiltViewModel<DashboardViewModel>()
                val state by viewModel.viewState.collectAsStateWithLifecycle()
                DashboardScreen(modifier = modifier, state = state, onEvent = viewModel::onEvent)
            }
            composable(route = Screen.SETTINGS.route) {

            }
        }
    }
}