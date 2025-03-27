package com.elpablo.fscslutsky.ui.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.core.utils.timeAgo
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListEvent
import com.google.firebase.Timestamp
import com.vk.sdk.api.wall.dto.WallWallItemDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentTypeDto
import sh.calvin.autolinktext.rememberAutoLinkText

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardListCard(
    post: WallWallItemDto,
    onEvent: (DashboardListEvent) -> Unit,
    onNavigateToDetail: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                //onNavigateToDetail(news.id)
            }
    ) {
//        GlideImage(
//            model = news.images?.first(),
//            contentDescription = null,
//            contentScale = ContentScale.FillWidth
//        )
        when (post) {
            is WallWallItemDto.WallWallpostFullDto -> {

                post.attachments?.forEach { attachment ->
                    when (attachment.type) {
                        WallWallpostAttachmentTypeDto.PHOTO -> {
                            GlideImage(
                                model = attachment.photo?.sizes?.last()?.url,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth
                            )
                        }
                        WallWallpostAttachmentTypeDto.VIDEO -> {
                            GlideImage(
                                model = attachment.video?.image?.last()?.url,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth
                            )
                        }
                        else -> {}
                    }
                }

                post.text?.let { text ->
                    Text(
                        text = AnnotatedString.rememberAutoLinkText(text
                            .substringBefore("[")),
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

            else -> {}
        }
    }
}