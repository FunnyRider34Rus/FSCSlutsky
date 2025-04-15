package com.elpablo.fscslutsky.core.utils

import androidx.compose.foundation.lazy.LazyListState
import com.google.firebase.Timestamp
import com.vk.sdk.api.video.dto.VideoVideoFullDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentTypeDto
import java.text.SimpleDateFormat
import java.util.Locale

fun timestampToDate(timestamp: Timestamp?): String? {
    val long = (timestamp?.seconds?.times(1000) ?: 0) + (timestamp?.nanoseconds?.div(1000000) ?: 0)
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return simpleDateFormat.format(long)
}

fun timestampToTime(timestamp: Timestamp?): String? {
    val long = (timestamp?.seconds?.times(1000) ?: 0) + (timestamp?.nanoseconds?.div(1000000) ?: 0)
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return simpleDateFormat.format(long)
}

fun longToDate(timestamp: Long?): String? {
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return simpleDateFormat.format(timestamp)
}

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount-1

fun timeAgo(timestamp: Timestamp?): String {
    val dateInMillis = (timestamp?.seconds?.times(1000) ?: 0) + (timestamp?.nanoseconds?.div(1000000) ?: 0)
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - dateInMillis
    if (diff < 0) return "в будущем"

    return when {
        diff >= YEAR_IN_MS -> formatUnit(diff, YEAR_IN_MS, "год", "года", "лет")
        diff >= MONTH_IN_MS -> formatUnit(diff, MONTH_IN_MS, "месяц", "месяца", "месяцев")
        diff >= DAY_IN_MS -> formatUnit(diff, DAY_IN_MS, "день", "дня", "дней")
        diff >= HOUR_IN_MS -> formatUnit(diff, HOUR_IN_MS, "час", "часа", "часов")
        diff >= MINUTE_IN_MS -> formatUnit(diff, MINUTE_IN_MS, "минуту", "минуты", "минут")
        diff >= SECOND_IN_MS -> formatUnit(diff, SECOND_IN_MS, "секунду", "секунды", "секунд")
        else -> "только что"
    }
}

private fun formatUnit(diff: Long, unitInMs: Long, form1: String, form2: String, form5: String): String {
    val units = (diff / unitInMs).toInt()
    return pluralForm(units, form1, form2, form5) + " назад"
}

private fun pluralForm(n: Int, form1: String, form2: String, form5: String): String {
    val num = n % 100
    val num1 = num % 10
    return when {
        num in 11..14 -> "$n $form5"
        num1 == 1 -> "$n $form1"
        num1 in 2..4 -> "$n $form2"
        else -> "$n $form5"
    }
}

private const val SECOND_IN_MS = 1000L
private const val MINUTE_IN_MS = 60 * SECOND_IN_MS
private const val HOUR_IN_MS = 60 * MINUTE_IN_MS
private const val DAY_IN_MS = 24 * HOUR_IN_MS
private const val MONTH_IN_MS = 30 * DAY_IN_MS
private const val YEAR_IN_MS = 365 * DAY_IN_MS

fun VideoVideoFullDto.toWallWallpostAttachmentDto(): WallWallpostAttachmentDto? {
    return WallWallpostAttachmentDto(video = this, type = WallWallpostAttachmentTypeDto.VIDEO)
}