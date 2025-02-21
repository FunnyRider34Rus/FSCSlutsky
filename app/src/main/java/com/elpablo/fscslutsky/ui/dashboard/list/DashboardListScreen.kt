package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elpablo.fscslutsky.core.utils.timestampToDate
import com.elpablo.fscslutsky.data.model.News
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailScreen

@Composable
fun DashboardListScreen(
    modifier: Modifier,
    state: DashboardListViewState
) {
    val scrollState = rememberLazyListState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var item by remember { mutableStateOf(News()) }
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (news in state.content) {
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
                    .clickable {
                        item = news
                        showBottomSheet = true
                        //onNavigate(news.id)
                    }
                ) {
                    AsyncImage(
                        model = news.images?.first(),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().clip(shape = RoundedCornerShape(16.dp))
                    )
                    news.title?.let { title ->
                        Text(
                            text = title,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    timestampToDate(news.date)?.let { date ->
                        Text(
                            text = date,
                            modifier = Modifier.fillMaxWidth().padding(end = 16.dp, bottom = 16.dp),
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
    if (showBottomSheet) {
        DashboardDetailScreen(item = item, onDismissRequest = { showBottomSheet = false })
    }
}