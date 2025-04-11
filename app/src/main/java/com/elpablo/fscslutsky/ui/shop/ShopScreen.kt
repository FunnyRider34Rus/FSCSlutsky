package com.elpablo.fscslutsky.ui.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShopScreen(modifier: Modifier) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 100.dp)
        .background(MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f))
    )
}