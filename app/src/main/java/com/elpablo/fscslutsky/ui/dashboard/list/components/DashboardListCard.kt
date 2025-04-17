package com.elpablo.fscslutsky.ui.dashboard.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListEvent
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewState

@Composable
fun DashboardListCard(
    uiState: DashboardListViewState,
    onEvent: (DashboardListEvent) -> Unit,
    indexOfPost: Int,
    onNavigateToDetail: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                onNavigateToDetail(uiState.posts[indexOfPost].id.toString())
            }
    ) {
        DashboardListMediaContentView(
            uiState = uiState,
            onEvent = onEvent,
            indexOfPost = indexOfPost
        )
        DashboardListTextView(
            uiState = uiState,
            indexOfPost = indexOfPost
        )
        DashboardListDateView(
            uiState = uiState,
            indexOfPost = indexOfPost
        )
    }
}