package com.elpablo.fscslutsky.ui.wall.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.data.model.Post
import com.elpablo.fscslutsky.ui.wall.WallEvent
import com.elpablo.fscslutsky.ui.wall.WallState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostItem(
    state: WallState,
    post: Post,
    onEvent: (WallEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        post.attachments?.forEach { attachment ->
            if (attachment.type == "photo") {
                val photoUrl = attachment.photo?.sizes[2]?.url
                if (photoUrl != null) {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        model = photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
        Text(
            text = post.text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleSmall
        )
    }
}


@Composable
@Preview
fun PostItemPreview() {
    PostItem(state = WallState(), post = Post(text = "Hello"), onEvent = { })
}