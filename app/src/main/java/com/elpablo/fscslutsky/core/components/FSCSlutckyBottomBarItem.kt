package com.elpablo.fscslutsky.core.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.core.navigation.Screen

sealed class FSCSlutckyBottomBarItem(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    data object Dashboard : FSCSlutckyBottomBarItem(
        route = Screen.DASHBOARD.route,
        label = R.string.bottom_bar_dashboard_label,
        icon = Icons.Filled.Dashboard
    )
    data object Matches : FSCSlutckyBottomBarItem(
        route = Screen.MATCHES.route,
        label = R.string.bottom_bar_matches_label,
        icon = Icons.Filled.SportsSoccer
    )
    data object Shop : FSCSlutckyBottomBarItem(
        route = Screen.SHOP.route,
        label = R.string.bottom_bar_shop_label,
        icon = Icons.Default.ShoppingCart
    )
    data object Profile : FSCSlutckyBottomBarItem(
        route = Screen.PROFILE.route,
        label = R.string.bottom_bar_profile_label,
        icon = Icons.Default.Person
    )
}

@Composable
fun FSCSlutckyBottomBar(navController: NavController) {
    val bottomBarScreens = listOf(
        FSCSlutckyBottomBarItem.Dashboard,
        FSCSlutckyBottomBarItem.Matches,
        FSCSlutckyBottomBarItem.Shop,
        FSCSlutckyBottomBarItem.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = bottomBarScreens.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        NavigationBar(
            containerColor = Color.White
        ) {
            bottomBarScreens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: FSCSlutckyBottomBarItem,
    currentDestination: NavDestination?,
    navController: NavController
) {
    NavigationBarItem(
        label = {
            Text(text = stringResource(screen.label))
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = stringResource(screen.label)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        alwaysShowLabel = false,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.primary,
            indicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun BottomBarPreview() {
    FSCSlutckyBottomBar(navController = rememberNavController())
}