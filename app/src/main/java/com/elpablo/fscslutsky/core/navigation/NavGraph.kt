@file:OptIn(ExperimentalMaterial3Api::class)

package com.elpablo.fscslutsky.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elpablo.fscslutsky.core.components.FSCSlutckyBottomBar
import com.elpablo.fscslutsky.ui.auth.AuthScreen
import com.elpablo.fscslutsky.ui.auth.AuthViewModel
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailScreen
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailViewModel
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListScreen
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewModel
import com.elpablo.fscslutsky.ui.matches.MatchesScreen
import com.elpablo.fscslutsky.ui.matches.MatchesViewModel
import com.elpablo.fscslutsky.ui.profile.ProfileScreen
import com.elpablo.fscslutsky.ui.profile.ProfileViewModel
import com.elpablo.fscslutsky.ui.shop.ShopScreen

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { FSCSlutckyBottomBar(navController = navController, scrollBehavior = scrollBehavior) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            }
        ) {
            composable(route = Screen.AUTH.route) {
                val viewModel = hiltViewModel<AuthViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                AuthScreen(
                    uiState = state,
                    uiEvent = viewModel::onEvent,
                    onNavigate = { navController.navigate(Screen.DASHBOARDLIST.route) }
                )
            }
            composable(route = Screen.DASHBOARDLIST.route) {
                val viewModel = hiltViewModel<DashboardListViewModel>()
                val state by viewModel.viewState.collectAsStateWithLifecycle()
                DashboardListScreen(
                    snackbar = snackbarHostState,
                    uiState = state,
                    onEvent = viewModel::onEvent,
                    onNavigateToDetail = { id ->
                        navController.navigate(Screen.DASHBOARDDETAIL.route + "/$id")
                    }
                )
            }
            composable(route = Screen.DASHBOARDDETAIL.route + "/{id}") { navEntry ->
                val id = navEntry.arguments?.getString("id")
                val viewModel = hiltViewModel<DashboardDetailViewModel>()
                val state by viewModel.viewState.collectAsStateWithLifecycle()
                DashboardDetailScreen(
                    state = state,
                    onEvent = viewModel::onEvent,
                    id = id
                )
            }
            composable(route = Screen.MATCHES.route) {
                val viewModel = hiltViewModel<MatchesViewModel>()
                val state by viewModel.viewState.collectAsStateWithLifecycle()
                MatchesScreen(uiState = state)
            }
            composable(route = Screen.SHOP.route) {
                ShopScreen()
            }
            composable(route = Screen.PROFILE.route) {
                val viewModel = hiltViewModel<ProfileViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                ProfileScreen(uiState = state)
            }
        }
    }
}