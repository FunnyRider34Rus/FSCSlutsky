package com.elpablo.fscslutsky.ui.dashboard.list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardListImageView(url: String?) {
    GlideImage(
        modifier = Modifier.blur(8.dp),
        model = url,
        contentDescription = null,
        contentScale = ContentScale.FillHeight
    )
    GlideImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}