package com.elpablo.fscslutsky.ui.dashboard.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.core.components.FSCSlutskyLoader
import com.elpablo.fscslutsky.core.utils.timestampToDate
import com.elpablo.fscslutsky.core.utils.timestampToTime
import com.elpablo.fscslutsky.ui.dashboard.list.DashboardListViewState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DashboardMatchTopCardView(
    uiState: DashboardListViewState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (uiState.matches.isNullOrEmpty() == true) {
            FSCSlutskyLoader()
        } else {
            GlideImage(
                modifier = Modifier.size(80.dp),
                model = uiState.homeClub?.logo,
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                uiState.matches.first()?.title?.let { title ->
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                uiState.matches.first()?.date?.let { date ->
                    Text(
                        text = timestampToDate(date) ?: "",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = timestampToTime(date) ?: "",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            GlideImage(
                modifier = Modifier.size(80.dp),
                model = uiState.awayClub?.logo,
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )
        }
    }
}