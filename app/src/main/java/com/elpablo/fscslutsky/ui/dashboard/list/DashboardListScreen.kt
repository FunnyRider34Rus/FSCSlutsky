package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.compose.foundation.background
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

@Composable
fun DashboardListScreen(
    snackbar: SnackbarHostState,
    uiState: DashboardListViewState,
    uiEvent: (DashboardListEvent) -> Unit,
    onNavigateToDetail: (String?) -> Unit
) {
    val scrollState = rememberLazyListState()
    if (uiState.isPostLoading) {
        FSCSlutskyLoader()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)),
        state = scrollState
    ) {
        uiState.posts?.size?.let { size ->
            items(size) { index ->
                if (uiState.posts[index].isPinned == true) {
                    DashboardListHeaderView(
                        uiState = uiState,
                        indexOfPost = index,
                        onEvent = uiEvent,
                        onNavigateToDetail = onNavigateToDetail
                    )
                } else {
                    DashboardListCardView(
                        modifier = Modifier.padding(16.dp),
                        uiState = uiState,
                        onEvent = uiEvent,
                        indexOfPost = index,
                        onNavigateToDetail = onNavigateToDetail
                    )
                }
            }
        }
    }
    if (scrollState.isScrolledToEnd()) {
        LaunchedEffect(scrollState.isScrolledToEnd()) {
            uiEvent(DashboardListEvent.NextRequest)
        }
    }
    if (uiState.isError) {
        LaunchedEffect(null) {
            uiState.error?.let { msg -> snackbar.showSnackbar(msg) }
        }
    }
}