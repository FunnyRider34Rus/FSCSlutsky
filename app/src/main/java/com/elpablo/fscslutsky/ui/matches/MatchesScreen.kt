@file:OptIn(ExperimentalGlideComposeApi::class)

package com.elpablo.fscslutsky.ui.matches

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.ui.matches.components.MatchesActualInfo
import com.elpablo.fscslutsky.ui.matches.components.MatchesPastInfo
import com.elpablo.fscslutsky.ui.matches.components.MatchesUpcomingInfo
import java.util.Date

@Composable
fun MatchesScreen(
    uiState: MatchesViewState
) {
    val scrollState = rememberLazyListState()
    if (uiState.isLoading) {
        FSCSlutskyLoader()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)),
        state = scrollState
    ) {
        uiState.matches?.size?.let { size ->
            items(size) { index ->
                uiState.matches[index].date?.toDate()?.let { date ->
                    if (index == uiState.index) {
                        MatchesActualInfo(
                            modifier = Modifier.padding(8.dp),
                            match = uiState.matches[index]
                        )
                        LaunchedEffect(uiState.index) {
                            scrollState.animateScrollToItem(uiState.index)
                        }
                    } else if (date < Date()) {
                        MatchesPastInfo(
                            modifier = Modifier.padding(8.dp),
                            match = uiState.matches[index])

                    } else {
                        MatchesUpcomingInfo(
                            modifier = Modifier.padding(8.dp),
                            match = uiState.matches[index]
                        )
                    }
                }
            }
        }
    }
}