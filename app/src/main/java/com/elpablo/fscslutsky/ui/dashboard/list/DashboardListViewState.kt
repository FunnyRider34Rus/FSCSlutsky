package com.elpablo.fscslutsky.ui.dashboard.list

import com.elpablo.fscslutsky.domain.model.News
import com.vk.sdk.api.wall.dto.WallWallItemDto

data class DashboardListViewState(
    val content: List<News> = emptyList(),
    val news: News = News(),
    val posts: List<WallWallItemDto> = emptyList(),
    val showBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
)
