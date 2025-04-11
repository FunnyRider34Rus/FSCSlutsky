package com.elpablo.fscslutsky.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.core.components.FSCSlutskyPageIndicator
import com.elpablo.fscslutsky.core.components.FSCSlutskyVideoPlayer
import com.elpablo.fscslutsky.core.utils.timeAgo
import com.elpablo.fscslutsky.domain.model.AttachmentType
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListEvent
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewState
import com.google.firebase.Timestamp
import sh.calvin.autolinktext.rememberAutoLinkText
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardListCard(
    uiState: DashboardListViewState,
    onEvent: (DashboardListEvent) -> Unit,
    indexOfPost: Int,
    onNavigateToDetail: (String?) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                onNavigateToDetail(uiState.posts[indexOfPost].id.toString())
            }
    ) {

        val pagerState = rememberPagerState(pageCount = {
            uiState.posts[indexOfPost].attachments?.size ?: 0
        })
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HorizontalPager(
                state = pagerState
            ) { indexOfAttachments ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .graphicsLayer {
                            val pageOffset = (
                                    (pagerState.currentPage - indexOfAttachments) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    when (uiState.posts[indexOfPost].attachments?.get(indexOfAttachments)?.type) {
                        AttachmentType.PHOTO -> {
                            GlideImage(
                                modifier = Modifier
                                    .blur(8.dp),
                                model = uiState.posts[indexOfPost].attachments?.get(indexOfAttachments)?.photo?.sizes?.get(2)?.url,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth
                            )
                            GlideImage(
                                model = uiState.posts[indexOfPost].attachments?.get(indexOfAttachments)?.photo?.sizes?.get(2)?.url,
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight
                            )
                        }

                        AttachmentType.VIDEO -> {
                            LaunchedEffect(true) {
                                onEvent(DashboardListEvent.GetVideoByID(uiState.posts[indexOfPost].attachments?.get(indexOfAttachments)?.video?.id, indexOfPost, indexOfAttachments))
                            }
                            if (uiState.posts[indexOfPost].attachments?.get(indexOfAttachments)?.video?.player != null) {
                                FSCSlutskyVideoPlayer(
                                    modifier = Modifier.fillMaxWidth().aspectRatio(16 / 9f),
                                    videoURI = uiState.posts[indexOfPost].attachments?.get(indexOfAttachments)?.video?.player.toString()
                                )
                            } else {
                                FSCSlutskyLoader()
                            }
                        }

                        else -> {
                            null
                        }
                    }
                    uiState.posts[indexOfPost].attachments?.size.let { count ->
                        if (count!! > 1) {
                            FSCSlutskyPageIndicator(
                                pageCount = count,
                                currentPageIndex = pagerState.currentPage,
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
            uiState.posts[indexOfPost].text?.let { text ->
                Text(
                    text = AnnotatedString
                        .rememberAutoLinkText(
                            text
                                .substringBefore("[")
                                .trimEnd()
                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = timeAgo(uiState.posts[indexOfPost].date?.toLong()?.let { Timestamp(it, 0) }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 16.dp),
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}