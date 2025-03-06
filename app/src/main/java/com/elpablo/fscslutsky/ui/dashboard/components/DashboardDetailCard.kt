package com.elpablo.fscslutsky.ui.dashboard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.elpablo.fscslutsky.core.components.FSCSlutskyPageIndicator
import com.elpablo.fscslutsky.core.utils.timestampToDate
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailViewState
import sh.calvin.autolinktext.rememberAutoLinkText

@Composable
fun DashboardDetailCard(state: DashboardDetailViewState) {
    val pagerState = rememberPagerState(pageCount = {
        state.content?.images?.size ?: 0
    })
    HorizontalPager(state = pagerState) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = state.content?.images?.get(pagerState.currentPage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            state.content?.images?.size?.let { count ->
                if (count > 1) {
                    FSCSlutskyPageIndicator(
                        pageCount = state.content.images.size,
                        currentPageIndex = pagerState.currentPage,
                        modifier = Modifier.align(
                            Alignment.BottomCenter
                        )
                    )
                }
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        state.content?.title?.let { title ->
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        }
        state.content?.body?.let { body ->
            Text(
                text = AnnotatedString.rememberAutoLinkText(body.replace("/n", "\n")),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        timestampToDate(state.content?.date)?.let { date ->
            Text(
                text = date,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 16.dp),
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}