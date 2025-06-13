package com.elpablo.fscslutsky.ui.dashboard.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListEvent
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewState

@Composable
fun DashboardListHeaderView(
    modifier: Modifier = Modifier,
    uiState: DashboardListViewState,
    indexOfPost: Int,
    onEvent: (DashboardListEvent) -> Unit,
    onNavigateToDetail: (String?) -> Unit
) {
    Column(modifier = modifier.padding(top = 80.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(start = 32.dp),
            text = "Главная новость",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
        DashboardListCardView(
            modifier = Modifier.padding(16.dp),
            uiState = uiState,
            onEvent = onEvent,
            indexOfPost = indexOfPost,
            onNavigateToDetail = onNavigateToDetail
        )
        Text(
            text = "Последние новости",
            modifier = Modifier.padding(start = 32.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}