package com.elpablo.fscslutsky.core.utils

import androidx.compose.foundation.lazy.LazyListState
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun timestampToDate(timestamp: Timestamp?): String? {
    val long = (timestamp?.seconds?.times(1000) ?: 0) + (timestamp?.nanoseconds?.div(1000000) ?: 0)
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return simpleDateFormat.format(long)
}

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount-1