package com.elpablo.fscslutsky.core.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.core.navigation.Screen

sealed class FSCSlutskyBottomBarItem(
    val route: String,
    @param:StringRes val label: Int,
    @param:DrawableRes val icon: Int
) {
    data object Dashboard : FSCSlutskyBottomBarItem(
        route = Screen.DASHBOARDLIST.route,
        label = R.string.bottom_bar_dashboard_label,
        icon = R.drawable.dashboard_outline
    )

    data object Matches : FSCSlutskyBottomBarItem(
        route = Screen.MATCHES.route,
        label = R.string.bottom_bar_matches_label,
        icon = R.drawable.matches_outline
    )

    data object Shop : FSCSlutskyBottomBarItem(
        route = Screen.SHOP.route,
        label = R.string.bottom_bar_shop_label,
        icon = R.drawable.shop_outline
    )

    data object Profile : FSCSlutskyBottomBarItem(
        route = Screen.PROFILE.route,
        label = R.string.bottom_bar_profile_label,
        icon = R.drawable.profile_outline
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FSCSlutckyBottomBar(navController: NavController, scrollBehavior: BottomAppBarScrollBehavior) {
    val bottomBarScreens = listOf(
        FSCSlutskyBottomBarItem.Dashboard,
        FSCSlutskyBottomBarItem.Matches,
        FSCSlutskyBottomBarItem.Shop,
        FSCSlutskyBottomBarItem.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = bottomBarScreens.any { it.route == currentDestination?.route }

    if (bottomBarDestination) {
        BottomAppBar(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 32.dp)
                .graphicsLayer(
                    shape = RoundedCornerShape(32.dp), clip = true
                ),
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
            scrollBehavior = scrollBehavior
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
    screen: FSCSlutskyBottomBarItem,
    currentDestination: NavDestination?,
    navController: NavController
) {
    NavigationBarItem(
        icon = {
            Icon(
                //modifier = Modifier.size(32.dp),
                painter = painterResource(screen.icon),
                contentDescription = stringResource(screen.label)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        //label =  { Text(text = stringResource(screen.label)) },
        alwaysShowLabel = false,
        onClick = {
            if (screen.route != currentDestination?.route) {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    //launchSingleTop = true
                }
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = MaterialTheme.colorScheme.tertiary,
            indicatorColor = Color.Transparent
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BottomBarPreview() {
    FSCSlutckyBottomBar(
        navController = rememberNavController(),
        scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    )
}