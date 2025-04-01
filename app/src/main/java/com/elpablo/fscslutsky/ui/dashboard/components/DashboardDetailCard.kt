package com.elpablo.fscslutsky.ui.dashboard.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.core.components.FSCSlutskyPageIndicator
import com.elpablo.fscslutsky.core.utils.timestampToDate
import com.elpablo.fscslutsky.ui.dashboard.detail.DashboardDetailViewState
import com.google.firebase.Timestamp
import com.vk.sdk.api.wall.dto.WallWallItemDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentTypeDto
import sh.calvin.autolinktext.rememberAutoLinkText
import kotlin.math.absoluteValue
import java.util.Date

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardDetailCard(state: DashboardDetailViewState) {
    when (state.content) {
        is WallWallItemDto.WallWallpostFullDto -> {
            val pagerState = rememberPagerState(pageCount = {
                state.content.attachments?.size ?: 0
            })

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                HorizontalPager(
                    state = pagerState
                ) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                            //Анимация перехода
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
                        val item = state.content.attachments?.get(page)
                        when (item?.type) {
                            WallWallpostAttachmentTypeDto.PHOTO -> {
                                GlideImage(
                                    modifier = Modifier.blur(8.dp),
                                    model = item.photo?.sizes?.last()?.url,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth
                                )
                                GlideImage(
                                    model = item.photo?.sizes?.last()?.url,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight
                                )
                            }

                            WallWallpostAttachmentTypeDto.VIDEO -> {
                                GlideImage(
                                    modifier = Modifier.blur(8.dp),
                                    model = item.video?.image?.last()?.url,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth
                                )
                                GlideImage(
                                    model = item.video?.image?.last()?.url,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight
                                )
                            }

                            else -> {
                                null
                            }
                        }
                        state.content.attachments?.size.let { count ->
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
                state.content.text?.let { text ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = AnnotatedString
                            .rememberAutoLinkText(text
                            .substringBefore("[")),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                timestampToDate(state.content.date?.toLong()?.let { Timestamp(Date(it * 1000)) })?.let {
                    Text(
                        text = it,
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
        else -> null
    }
}