package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DashboardDetailCard(state = state)
    }
}