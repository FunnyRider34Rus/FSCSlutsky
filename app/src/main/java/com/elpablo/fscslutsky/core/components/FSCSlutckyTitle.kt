package com.elpablo.fscslutsky.core.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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