@file:OptIn(ExperimentalGlideComposeApi::class)

package com.elpablo.fscslutsky.ui.matches.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.elpablo.fscslutsky.core.utils.timestampToDate
import com.elpablo.fscslutsky.domain.model.FullMatchInfo

@Composable
fun MatchesActualInfo(modifier: Modifier = Modifier, match: FullMatchInfo) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        GlideImage(
            modifier = Modifier
                .size(128.dp)
                .offset(x = (-64).dp)
                .align(Alignment.CenterStart),
            model = match.homeClub?.logo,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center),
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
            match.locationName?.let { location ->
                Text(
                    text = location,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        GlideImage(
            modifier = Modifier
                .size(128.dp)
                .offset(x = 64.dp)
                .align(Alignment.CenterEnd),
            model = match.awayClub?.logo,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}