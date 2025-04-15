package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.ui.dashboard.components.DashboardDetailCard

@Composable
fun DashboardDetailScreen(
    modifier: Modifier,
    state: DashboardDetailViewState,
    onEvent: (DashboardDetailEvent) -> Unit,
    id: String?
) {
    LaunchedEffect(null) {
        onEvent(DashboardDetailEvent.GetPostByID(id?.toInt()))
    }
    if (state.isPostLoading) {
        FSCSlutskyLoader()
    }
    DashboardDetailCard(modifier = modifier, uiState = state, onEvent = onEvent)
}