package com.elpablo.fscslutsky.core.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.core.navigation.Screen

@Composable
fun FSCSSlutckyTitle(navController: NavController) {
    var id by remember { mutableIntStateOf(0) }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            Screen.DASHBOARDLIST.route -> {
                id = R.string.bottom_bar_dashboard_label
            }

            Screen.MATCHES.route -> {
                id = R.string.bottom_bar_matches_label
            }

            Screen.SHOP.route -> {
                id = R.string.bottom_bar_shop_label
            }

            Screen.PROFILE.route -> {
                id = R.string.bottom_bar_profile_label
            }

            else -> id = 0
        }
    }
    key(id) {
        if (id != 0) {
            HeaderTopAppBar(id)
        } else {
            ButtonBackwardTopAppBar { navController.popBackStack() }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderTopAppBar(@StringRes id: Int) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id),
                color = MaterialTheme.colorScheme.primary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonBackwardTopAppBar(onClick: () -> Unit) {
    TopAppBar(
        title = { Text("") },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Button back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
}