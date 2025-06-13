package com.elpablo.fscslutsky.ui.dashboard.list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewState
import sh.calvin.autolinktext.rememberAutoLinkText

@Composable
fun DashboardListTextView(
    modifier: Modifier = Modifier,
    uiState: DashboardListViewState,
    indexOfPost: Int
) {
    uiState.posts?.get(indexOfPost)?.text?.let { text ->
        Text(
            text = AnnotatedString
                .rememberAutoLinkText(
                    text
                        .substringBefore("[")
                        .trimEnd()
                ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}