package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.ui.dashboard.components.DashboardDetailCard

@Composable
fun DashboardDetailScreen(
    modifier: Modifier,
    viewModel: DashboardDetailViewModel,
    state: DashboardDetailViewState,
    id: String?
) {
    LaunchedEffect(null) {
        viewModel.getPostByID(id?.toInt())
    }
    if (state.isLoading) {
        FSCSlutskyLoader()
    }
    DashboardDetailCard(modifier = modifier, state = state)
}