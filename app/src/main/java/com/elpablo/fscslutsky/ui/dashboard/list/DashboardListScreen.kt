package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.core.utils.isScrolledToEnd
import com.elpablo.fscslutsky.ui.dashboard.list.components.DashboardListCardView
import com.elpablo.fscslutsky.ui.dashboard.list.components.DashboardListHeaderView
import com.elpablo.fscslutsky.ui.dashboard.list.components.DashboardMatchTopCardView

@Composable
fun DashboardListScreen(
    modifier: Modifier = Modifier,
    snackbar: SnackbarHostState,
    uiState: DashboardListViewState,
    onEvent: (DashboardListEvent) -> Unit,
    onNavigateToDetail: (String?) -> Unit
) {
    val scrollState = rememberLazyListState()
    if (uiState.isPostLoading) {
        FSCSlutskyLoader()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)),
            state = scrollState
        ) {
            uiState.posts?.size?.let { size ->
                items(size) { index ->
                    if (uiState.posts[index].isPinned == true) {
                        DashboardListHeaderView(
                            uiState = uiState,
                            indexOfPost = index,
                            onEvent = onEvent,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    } else {
                        DashboardListCardView(
                            modifier = Modifier.padding(16.dp),
                            uiState = uiState,
                            onEvent = onEvent,
                            indexOfPost = index,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }
                }
            }
        }
        DashboardMatchTopCardView(uiState = uiState)
    }
    if (scrollState.isScrolledToEnd()) {
        LaunchedEffect(scrollState.isScrolledToEnd()) {
            onEvent(DashboardListEvent.NextRequest)
        }
    }
    if (uiState.isError) {
        LaunchedEffect(null) {
            uiState.error?.let { msg -> snackbar.showSnackbar(msg) }
        }
    }
}