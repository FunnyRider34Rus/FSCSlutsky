package com.elpablo.fscslutsky.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.core.utils.timestampToDate

@Composable
fun DashboardScreen(
    modifier: Modifier,
    state: DashboardViewState,
    onEvent: (DashboardEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    )
    {
        Text(
            text = stringResource(R.string.bottom_bar_dashboard_label ),
            modifier = Modifier.fillMaxWidth().padding(16.dp).padding(start = 16.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        LazyColumn(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiary).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
        ) {
            for (news in state.content) {
                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(color = Color.White)
                    ) {
                        AsyncImage(
                            model = news.images?.first(),
                            contentScale = ContentScale.FillWidth,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = news.title.toString(),
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            color = Color.Black,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = timestampToDate(news.date),
                            modifier = Modifier.fillMaxWidth().padding(end = 16.dp, bottom = 16.dp),
                            textAlign = TextAlign.End,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}