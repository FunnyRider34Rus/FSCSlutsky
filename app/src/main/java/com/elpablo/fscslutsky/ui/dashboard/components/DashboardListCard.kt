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
import com.elpablo.fscslutsky.core.components.FSCSlutskyPageIndicator
import com.elpablo.fscslutsky.core.components.FSCSlutskyVideoPlayer
import com.elpablo.fscslutsky.core.utils.timeAgo
import com.elpablo.fscslutsky.domain.model.AttachmentType
import com.elpablo.fscslutsky.domain.model.VkWall
import com.google.firebase.Timestamp
import sh.calvin.autolinktext.rememberAutoLinkText
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardListCard(
    post: VkWall,
    onNavigateToDetail: (String?) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {

        val pagerState = rememberPagerState(pageCount = {
            post.attachments?.size ?: 0
        })
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HorizontalPager(
                state = pagerState
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
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
                        }
                        .clickable {
                            onNavigateToDetail(post.id.toString())
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val item = post.attachments?.get(page)
                    when (item?.type) {
                         AttachmentType.PHOTO -> {
                            GlideImage(
                                modifier = Modifier
                                    .blur(8.dp),
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

                        AttachmentType.VIDEO -> {
                            item.video?.player?.let { url -> FSCSlutskyVideoPlayer(videoUrl = url) }
//                            GlideImage(
//                                modifier = Modifier
//                                    .blur(8.dp),
//                                model = item.video?.image?.last()?.url,
//                                contentDescription = null,
//                                contentScale = ContentScale.FillWidth
//                            )
//                            GlideImage(
//                                model = item.video?.image?.last()?.url,
//                                contentDescription = null,
//                                contentScale = ContentScale.FillHeight
//                            )
                        }

                        else -> {
                            null
                        }
                    }
                    post.attachments?.size.let { count ->
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
            post.text?.let { text ->
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
                text = timeAgo(post.date?.toLong()?.let { Timestamp(it, 0) }),
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