package com.elpablo.fscslutsky.ui.dashboard.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.core.components.FSCSlutskyPageIndicator
import com.elpablo.fscslutsky.core.components.FSCSlutskyVideoPlayer
import com.elpablo.fscslutsky.domain.model.AttachmentType
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailEvent
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailViewState
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardDetailCard(
    modifier: Modifier = Modifier,
    uiState: DashboardDetailViewState,
    onEvent: (DashboardDetailEvent) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = {
        uiState.content?.attachments?.size ?: 0
    })
    HorizontalPager(
        modifier = modifier
            .fillMaxSize(),
        state = pagerState,
        verticalAlignment = Alignment.CenterVertically
    ) { indexOfAttachments ->
        Box(
            modifier = Modifier
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
            val item = uiState.content?.attachments?.get(indexOfAttachments)
            when (item?.type) {
                AttachmentType.PHOTO -> {
                    GlideImage(
                        modifier = Modifier.blur(8.dp),
                        model = item.photo?.sizes?.last()?.url,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )
                    GlideImage(
                        model = item.photo?.sizes?.last()?.url,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }

                AttachmentType.VIDEO -> {
                    LaunchedEffect(true) {
                        onEvent(DashboardDetailEvent.GetVideoByID(item.video?.id, indexOfAttachments))
                    }
                    if (!uiState.isVideoLoading) {
                        item.video?.player?.let { url ->
                            FSCSlutskyVideoPlayer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16 / 9f),
                                videoURI = url
                            )
                        }
                    }
                }
                null -> {  }
            }
            uiState.content?.attachments?.size.let { count ->
                count?.let { count ->
                    if (count > 1) {
                        FSCSlutskyPageIndicator(
                            pageCount = count,
                            currentPageIndex = pagerState.currentPage,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}