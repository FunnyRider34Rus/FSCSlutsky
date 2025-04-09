package com.elpablo.fscslutsky.ui.dashboard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
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
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailViewState
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentTypeDto
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardDetailCard(modifier: Modifier = Modifier, state: DashboardDetailViewState) {
    val pagerState = rememberPagerState(pageCount = {
        state.content?.attachments?.size ?: 0
    })
    HorizontalPager(
        modifier = modifier
            .fillMaxSize(),
        state = pagerState,
        verticalAlignment = Alignment.CenterVertically
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
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
            val item = state.content?.attachments?.get(page)
            when (item?.type) {
                WallWallpostAttachmentTypeDto.PHOTO -> {
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

                WallWallpostAttachmentTypeDto.VIDEO -> {
                    GlideImage(
                        modifier = Modifier.blur(8.dp),
                        model = item.video?.image?.last()?.url,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )
                    GlideImage(
                        model = item.video?.image?.last()?.url,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }

                else -> {
                    null
                }
            }
            state.content?.attachments?.size.let { count ->
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
}