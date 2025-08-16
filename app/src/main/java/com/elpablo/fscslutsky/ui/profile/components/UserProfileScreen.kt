package com.elpablo.fscslutsky.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.R
import com.elpablo.fscslutsky.ui.profile.ProfileEvent
import com.elpablo.fscslutsky.ui.profile.ProfileViewState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    uiState: ProfileViewState,
    onEvent: (ProfileEvent) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlideImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = uiState.user?.photo200,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = uiState.user?.firstName.toString() + " " + uiState.user?.lastName.toString(),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onEvent(ProfileEvent.logout)
                },
            painter = painterResource(R.drawable.logout_outline),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}