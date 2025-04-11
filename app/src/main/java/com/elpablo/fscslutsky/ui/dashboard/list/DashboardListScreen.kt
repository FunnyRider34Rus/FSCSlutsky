package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.core.utils.isScrolledToEnd
import com.elpablo.fscslutsky.ui.dashboard.components.DashboardListCard

@Composable
fun DashboardListScreen(
    modifier: Modifier,
    uiState: DashboardListViewState,
    onEvent: (DashboardListEvent) -> Unit,
    onNavigateToDetail: (String?) -> Unit
) {
    val scrollState = rememberLazyListState()
    if (uiState.isPostLoading) {
        FSCSlutskyLoader()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)),
        state = scrollState,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(uiState.posts.size) { index ->
            DashboardListCard(
                uiState = uiState,
                onEvent = onEvent,
                indexOfPost = index,
                onNavigateToDetail = onNavigateToDetail
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
    if (scrollState.isScrolledToEnd()) {
        LaunchedEffect(scrollState.isScrolledToEnd()) {
            onEvent(DashboardListEvent.NextRequest)
        }
    }
}