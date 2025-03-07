package com.elpablo.fscslutsky.ui.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.core.utils.timeAgo
import com.elpablo.fscslutsky.data.model.News
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListEvent

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardListCard(
    news: News,
    onEvent: (DashboardListEvent) -> Unit,
    onNavigateToDetail: (String?) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onNavigateToDetail(news.id)
        }
    ) {
        GlideImage(
            model = news.images?.first(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        news.title?.let { title ->
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Text(
            text = timeAgo(news.date),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, bottom = 16.dp),
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}