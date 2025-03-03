package com.elpablo.fscslutsky.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun FSCSlutskyPageIndicator(pageCount: Int?, currentPageIndex: Int, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            pageCount?.let { size ->
                repeat(size) { iteration ->
                    val color =
                        if (currentPageIndex == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                    Box(
                        modifier = modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}