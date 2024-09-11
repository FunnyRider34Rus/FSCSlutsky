package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
    id: String?,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(null) {
        viewModel.fetchNews(id)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    )
    {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { onNavigateBack.invoke() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Button back",
                        tint =  MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            AsyncImage(
                model = state.content?.images?.first(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            state.content?.title?.let { title ->
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            state.content?.body?.let { body ->
                Text(
                    text = body.replace("/n", "\n"),
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}