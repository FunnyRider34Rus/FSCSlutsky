package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardDetailScreen(
    modifier: Modifier,
    viewModel: DashboardDetailViewModel,
    state: DashboardDetailViewState,
    id: String?
) {
    LaunchedEffect(null) {
        viewModel.fetchNews(id)
    }
    Box(
        modifier = modifier
    )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .verticalScroll(rememberScrollState())) {
            AsyncImage(
                model = state.content?.images?.first(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            state.content?.title?.let { title ->
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            state.content?.body?.let { body ->
                Text(
                    text = body.replace("/n", "\n"),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}