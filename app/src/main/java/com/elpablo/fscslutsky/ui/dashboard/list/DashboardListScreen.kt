package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardListScreen(
    modifier: Modifier,
    state: DashboardListViewState,
    onNavigate: (String?) -> Unit
) {
    Column(modifier = modifier) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.bottom_bar_dashboard_label),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
        LazyColumn(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.tertiary)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
        ) {
            for (news in state.content) {
                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(color = Color.White)
                        .clickable { onNavigate(news.id) }
                    ) {
                        AsyncImage(
                            model = news.images?.first(),
                            contentScale = ContentScale.FillWidth,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                        news.title?.let { title ->
                            Text(
                                text = title,
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                color = Color.Black,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        timestampToDate(news.date)?.let { date ->
                            Text(
                                text = date,
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
}