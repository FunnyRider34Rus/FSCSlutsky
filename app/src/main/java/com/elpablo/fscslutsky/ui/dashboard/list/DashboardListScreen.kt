package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.core.utils.LocalSnackbarHostState
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
    val snackbar = LocalSnackbarHostState.current
    if (state.isLoading) {
        FSCSlutskyLoader()
    }
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        state = scrollState
    ) {
        items(state.content.size) { index ->
            DashboardListCard(
                news = state.content[index],
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
    if (state.isError) {
        LaunchedEffect(state.isError) {
            snackbar.showSnackbar(message = state.error)
        }
    }
}