package com.elpablo.fscslutsky.ui.dashboard.list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.core.utils.timeAgo
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewState
import com.google.firebase.Timestamp

@Composable
fun DashboardListDateView(
    modifier: Modifier = Modifier,
    uiState: DashboardListViewState,
    indexOfPost: Int
) {
    Text(
        text = timeAgo(uiState.posts?.get(indexOfPost)?.date?.toLong()?.let { Timestamp(it, 0) }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, bottom = 16.dp),
        textAlign = TextAlign.End,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.labelLarge
    )
}