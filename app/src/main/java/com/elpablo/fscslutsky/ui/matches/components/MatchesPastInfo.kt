@file:OptIn(ExperimentalGlideComposeApi::class)

package com.elpablo.fscslutsky.ui.matches.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.elpablo.fscslutsky.core.utils.timestampToDate
import com.elpablo.fscslutsky.domain.model.FullMatchInfo

@Composable
fun MatchesPastInfo(modifier: Modifier = Modifier, match: FullMatchInfo) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                model = match.homeClub?.logo,
                contentDescription = null,
                contentScale = ContentScale.Inside
            )
        }
        Text(
            text = match.homeScore.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            match.title?.let { title ->
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            timestampToDate(match.date)?.let { date ->
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Text(
            text = match.awayScore.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Box(
            modifier = Modifier.size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp),
                model = match.awayClub?.logo,
                contentDescription = null,
                contentScale = ContentScale.Inside
            )
        }
    }
}