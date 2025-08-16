package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.ui.dashboard.detail.components.DashboardDetailCard

@Composable
fun DashboardDetailScreen(
    uiState: DashboardDetailViewState,
    uiEvent: (DashboardDetailEvent) -> Unit,
    id: String?
) {
    LaunchedEffect(null) {
        uiEvent(DashboardDetailEvent.GetPostByID(id?.toInt()))
    }
    if (uiState.isPostLoading) {
        FSCSlutskyLoader()
    }
    DashboardDetailCard(modifier = Modifier, uiState = uiState, onEvent = uiEvent)
}