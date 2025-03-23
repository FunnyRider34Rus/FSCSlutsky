package com.elpablo.fscslutsky.ui.wall

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.ui.wall.components.PostItem

@Composable
fun WallScreen(modifier: Modifier, state: WallState, onEvent: (WallEvent) -> Unit) {
    val scrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
        onEvent(WallEvent.LoadPosts)
    }

    if (state.isLoading) {
        FSCSlutskyLoader(modifier = modifier)
    } else if (state.error != null) {
        Text(text = state.error)
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state = scrollState
        ) {
            items(state.posts.size) { index ->
                PostItem(post = state.posts[index])
            }
        }
    }
}
