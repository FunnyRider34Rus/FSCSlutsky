package com.elpablo.fscslutsky.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
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
import com.elpablo.fscslutsky.core.components.FSCSSlutckyTitle
import com.elpablo.fscslutsky.core.components.FSCSlutckyBottomBar
import com.elpablo.fscslutsky.ui.dashboard.DashboardListScreen
import com.elpablo.fscslutsky.ui.dashboard.DashboardListViewModel
import com.elpablo.fscslutsky.ui.matches.MatchesScreen
import com.elpablo.fscslutsky.ui.profile.ProfileScreen
import com.elpablo.fscslutsky.ui.shop.ShopScreen

@Composable
fun SetupNavGraph(navController: NavHostController, startDestination: String) {
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = { FSCSSlutckyTitle(navController = navController) },
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
            composable(route = Screen.DASHBOARDLIST.route) {
                val viewModel = hiltViewModel<DashboardListViewModel>()
                val state by viewModel.viewState.collectAsStateWithLifecycle()
                DashboardListScreen(
                    modifier = modifier,
                    state = state,
                    onEvent = viewModel::onEvent
                )
            }
            composable(route = Screen.MATCHES.route) {
                MatchesScreen(modifier = modifier)
            }
            composable(route = Screen.SHOP.route) {
                ShopScreen(modifier = modifier)
            }
            composable(route = Screen.PROFILE.route) {
                ProfileScreen(modifier = modifier)
            }
        }
    }
}