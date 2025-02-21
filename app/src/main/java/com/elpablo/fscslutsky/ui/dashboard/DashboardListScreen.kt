package com.elpablo.fscslutsky.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.ui.dashboard.component.DashboardDetailScreen
import com.elpablo.fscslutsky.ui.dashboard.component.DashboardListCard

@Composable
fun DashboardListScreen(
    modifier: Modifier,
    state: DashboardListViewState,
    onEvent: (DashboardListEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (news in state.content) {
            item {
                DashboardListCard(
                    news = news,
                    onEvent = onEvent
                )
            }
        }
    }
    if (state.showBottomSheet) {
        DashboardDetailScreen(item = state.news, onDismissRequest = { onEvent(DashboardListEvent.BottomSheetDismiss) })
    }
}