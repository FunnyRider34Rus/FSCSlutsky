package com.elpablo.fscslutsky.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elpablo.fscslutsky.ui.profile.ProfileEvent

@Composable
fun LicensingScreen(
    modifier: Modifier = Modifier,
    onEvent: (ProfileEvent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable {
                onEvent(ProfileEvent.showLicensing)
            }
        ,
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Условия пользования",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}