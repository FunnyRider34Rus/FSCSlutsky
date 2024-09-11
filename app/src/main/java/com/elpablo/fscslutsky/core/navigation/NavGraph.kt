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
import com.elpablo.fscslutsky.core.components.FSCSlutckyBottomBar
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailScreen
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailViewModel
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListScreen
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    Scaffold(
        bottomBar = { FSCSlutckyBottomBar(navController = navController) }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(route = Screen.DASHBOARDLIST.route) {
                val viewModel = hiltViewModel<DashboardListViewModel>()
                val state by viewModel.viewState.collectAsStateWithLifecycle()
                DashboardListScreen(modifier = modifier, state = state, onNavigate = { id ->
                    navController.navigate(Screen.DASHBOARDDETAIL.route+"/$id")
                })
            }
            composable(route = Screen.DASHBOARDDETAIL.route + "/{id}") { navEntry ->
                val id = navEntry.arguments?.getString("id")
                val viewModel = hiltViewModel<DashboardDetailViewModel>()
                val state by viewModel.viewState.collectAsStateWithLifecycle()
                DashboardDetailScreen(
                    modifier = modifier,
                    viewModel = viewModel,
                    state = state,
                    id =id,
                    onNavigateBack = { navController.popBackStack() }
                )

            }
            composable(route = Screen.MATCHES.route) {

            }
            composable(route = Screen.SHOP.route) {

            }
            composable(route = Screen.PROFILE.route) {

            }
        }
    }
}