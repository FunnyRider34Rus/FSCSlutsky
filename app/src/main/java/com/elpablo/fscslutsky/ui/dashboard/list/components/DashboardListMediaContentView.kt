package com.elpablo.fscslutsky.ui.dashboard.list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.core.components.FSCSlutskyPageIndicator
import com.elpablo.fscslutsky.domain.model.AttachmentType
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListEvent
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewState
import kotlin.math.absoluteValue

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardListMediaContentView(
    modifier: Modifier = Modifier,
    uiState: DashboardListViewState,
    onEvent: (DashboardListEvent) -> Unit,
    indexOfPost: Int,
) {
    val pagerState = rememberPagerState(pageCount = {
        uiState.posts?.get(indexOfPost)?.attachments?.size ?: 0
    })
    HorizontalPager(
        modifier = modifier,
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
            when (uiState.posts?.get(indexOfPost)?.attachments?.get(indexOfAttachments)?.type) {
                AttachmentType.PHOTO -> {
                    GlideImage(
                        modifier = Modifier
                            .blur(8.dp),
                        model = uiState.posts[indexOfPost].attachments?.get(
                            indexOfAttachments
                        )?.photo?.sizes?.get(2)?.url,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    GlideImage(
                        model = uiState.posts[indexOfPost].attachments?.get(
                            indexOfAttachments
                        )?.photo?.sizes?.get(2)?.url,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )
                }

                AttachmentType.VIDEO -> {
                    GlideImage(
                        modifier = Modifier
                            .blur(8.dp),
                        model = uiState.posts[indexOfPost].attachments?.get(
                            indexOfAttachments
                        )?.video?.image?.get(4)?.url,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    GlideImage(
                        model = uiState.posts[indexOfPost].attachments?.get(
                            indexOfAttachments
                        )?.video?.image?.get(4)?.url,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(32.dp)
                            .align(Alignment.BottomEnd),
                        painter = painterResource(R.drawable.video_advertisement),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                null -> {}
            }
            uiState.posts?.get(indexOfPost)?.attachments?.size.let { count ->
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