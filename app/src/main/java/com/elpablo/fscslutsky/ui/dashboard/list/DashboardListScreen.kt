package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.core.utils.isScrolledToEnd
import com.elpablo.fscslutsky.ui.dashboard.components.DashboardListCard

@Composable
fun DashboardListScreen(
    modifier: Modifier,
    state: DashboardListViewState,
    onEvent: (DashboardListEvent) -> Unit,
    onNavigateToDetail: (String?) -> Unit
) {
    val scrollState = rememberLazyListState()
    if (state.isLoading) {
        FSCSlutskyLoader()
    }
    LazyColumn(
        modifier = modifier,
        state = scrollState
    ) {
        items(state.posts.size) { index ->
            DashboardListCard(
                post = state.posts[index],
                onEvent = onEvent,
                onNavigateToDetail = onNavigateToDetail
            )
        }

    }
    if (scrollState.isScrolledToEnd()) {
        LaunchedEffect(scrollState.isScrolledToEnd()) {
            onEvent(DashboardListEvent.NextRequest)
        }
    }
}