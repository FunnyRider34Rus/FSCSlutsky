@file:OptIn(ExperimentalGlideComposeApi::class)

package com.elpablo.fscslutsky.ui.matches

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader

@Composable
fun MatchesScreen(
    uiState: MatchesViewState
) {
    if (uiState.isLoading) {
        FSCSlutskyLoader()
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        uiState.matches?.size?.let { size ->
            items(size) { index ->
                GlideImage(
                    model = uiState.matches[index].homeClub?.logo,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}