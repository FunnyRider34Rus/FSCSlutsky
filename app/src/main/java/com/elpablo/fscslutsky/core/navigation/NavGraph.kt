@file:OptIn(ExperimentalMaterial3Api::class)

package com.elpablo.fscslutsky.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elpablo.fscslutsky.core.components.FSCSSlutskyTitle
import com.elpablo.fscslutsky.core.components.FSCSlutckyBottomBar
import com.elpablo.fscslutsky.ui.auth.AuthScreen
import com.elpablo.fscslutsky.ui.auth.AuthViewModel
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailScreen
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailViewModel
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListScreen
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewModel
import com.elpablo.fscslutsky.ui.matches.MatchesScreen
import com.elpablo.fscslutsky.ui.profile.ProfileScreen
import com.elpablo.fscslutsky.ui.profile.ProfileViewModel
import com.elpablo.fscslutsky.ui.shop.ShopScreen
import com.elpablo.fscslutsky.ui.wall.WallScreen
import com.elpablo.fscslutsky.ui.wall.WallViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        topBar = { FSCSSlutskyTitle(navController = navController) },
        bottomBar = { FSCSlutckyBottomBar(navController = navController) }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
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
                    modifier = modifier,
                    uiState = state,
                    onEvent = viewModel::onEvent,
                    onNavigate = { navController.navigate(Screen.WALL.route) }
                    )
            }
            composable(route = Screen.WALL.route) {
                val viewModel = hiltViewModel<WallViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                WallScreen(
                    modifier = modifier,
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
            composable(route = Screen.DASHBOARDLIST.route) {
                val viewModel = hiltViewModel<DashboardListViewModel>()
                val state by viewModel.viewState.collectAsStateWithLifecycle()
                DashboardListScreen(
                    modifier = modifier,
                    state = state,
                    onEvent = viewModel::onEvent,
                    onNavigateToDetail = { id ->
                        navController.navigate(Screen.DASHBOARDDETAIL.route+"/$id")
                    }
                )
            }
            composable(route = Screen.DASHBOARDDETAIL.route + "/{id}") { navEntry ->
                val id = navEntry.arguments?.getString("id")
                val viewModel = hiltViewModel<DashboardDetailViewModel>()
                val state by viewModel.viewState.collectAsStateWithLifecycle()
                DashboardDetailScreen(
                    modifier = modifier,
                    viewModel = viewModel,
                    state = state,
                    id =id
                )
            }
            composable(route = Screen.MATCHES.route) {
                MatchesScreen(modifier = modifier)
            }
            composable(route = Screen.SHOP.route) {
                ShopScreen(modifier = modifier)
            }
            composable(route = Screen.PROFILE.route) {
                val viewModel = hiltViewModel<ProfileViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                ProfileScreen(
                    modifier = modifier,
                    uiState = state
                )
            }
        }
    }
}