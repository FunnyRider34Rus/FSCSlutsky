package com.elpablo.fscslutsky.ui.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.core.utils.isScrolledToEnd
import com.elpablo.fscslutsky.ui.dashboard.component.DashboardDetailScreen
import com.elpablo.fscslutsky.ui.dashboard.component.DashboardListCard

@Composable
fun DashboardListScreen(
    modifier: Modifier,
    state: DashboardListViewState,
    onEvent: (DashboardListEvent) -> Unit
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.content.size) { index ->
            DashboardListCard(
                news = state.content[index],
                onEvent = onEvent
            )
        }
    }
    if (scrollState.isScrolledToEnd()) {
        LaunchedEffect(scrollState.isScrolledToEnd()) {
            onEvent(DashboardListEvent.NextRequest)
        }
    }
    if (state.showBottomSheet) {
        DashboardDetailScreen(
            item = state.news,
            onDismissRequest = { onEvent(DashboardListEvent.BottomSheetDismiss) })
    }
}