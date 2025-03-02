package com.elpablo.fscslutsky.ui.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.elpablo.fscslutsky.core.utils.timestampToDate
import com.elpablo.fscslutsky.data.model.News
import sh.calvin.autolinktext.rememberAutoLinkText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardDetailScreen(
    item: News,
    onDismissRequest: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = {
        item.images?.size ?: 0
    })
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier
            .padding(top = 48.dp)
            .fillMaxHeight(),
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        ),
        containerColor = MaterialTheme.colorScheme.background
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HorizontalPager(state = pagerState) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        model = item.images?.get(pagerState.currentPage),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.FillWidth
                    )
                    item.images?.size?.let { count ->
                        if (count > 1) {
                            PagerIndicator(
                                pageCount = item.images.size,
                                currentPageIndex = pagerState.currentPage,
                                modifier = Modifier.align(Alignment.BottomCenter
                                )
                            )
                        }
                    }
                }
            }
            item.title?.let { title ->
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            item.body?.let { body ->
                Text(
                    text = AnnotatedString.rememberAutoLinkText(body.replace("/n", "\n")),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            timestampToDate(item.date)?.let { date ->
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
}

@Composable
fun PagerIndicator(pageCount: Int?, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            pageCount?.let { size ->
                repeat(size) { iteration ->
                    val color =
                        if (currentPageIndex == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    Box(
                        modifier = modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}