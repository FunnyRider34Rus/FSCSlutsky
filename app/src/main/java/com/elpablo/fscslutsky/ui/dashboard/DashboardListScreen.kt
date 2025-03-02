package com.elpablo.fscslutsky.ui.dashboard

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.elpablo.fscslutsky.core.navigation.LocalSnackbarHostState
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
    val snackbar = LocalSnackbarHostState.current
    LazyColumn(
        modifier = modifier,
        state = scrollState
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
    if (state.isError) {
        LaunchedEffect(true) {
            snackbar.showSnackbar(message = state.error)
        }
    }
}