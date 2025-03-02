package com.elpablo.fscslutsky.core.utils

import androidx.compose.foundation.lazy.LazyListState
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

fun timestampToDate(timestamp: Timestamp?): String? {
    val long = (timestamp?.seconds?.times(1000) ?: 0) + (timestamp?.nanoseconds?.div(1000000) ?: 0)
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return simpleDateFormat.format(long)
}

fun timeAgo(timestamp: Timestamp?): String {
    val timeInMillis = (timestamp?.seconds?.times(1000) ?: 0) + (timestamp?.nanoseconds?.div(1000000) ?: 0)
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - timeInMillis

    val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)
    val weeks = days / 7
    val months = days / 30
    val years = days / 365

    return when {
        seconds < 60 -> "$seconds с назад"
        minutes < 60 -> "$minutes м назад"
        hours < 24 -> "$hours ч назад"
        days < 7 -> "$days д назад"
        weeks < 4 -> "$weeks н назад"
        months < 12 -> "$months мес назад"
        else -> "$years г назад"
    }
}

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount-1