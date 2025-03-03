package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.core.utils.LocalSnackbarHostState
import com.elpablo.fscslutsky.ui.dashboard.components.DashboardDetailCard

@Composable
fun DashboardDetailScreen(
    modifier: Modifier,
    viewModel: DashboardDetailViewModel,
    state: DashboardDetailViewState,
    id: String?
) {
    LaunchedEffect(null) {
        viewModel.fetchNews(id)
    }
    val snackbar = LocalSnackbarHostState.current
    if (state.isLoading) {
        FSCSlutskyLoader()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DashboardDetailCard(state = state)
    }
    if (state.isError) {
        LaunchedEffect(state.isError) {
            snackbar.showSnackbar(message = state.error)
        }
    }
}