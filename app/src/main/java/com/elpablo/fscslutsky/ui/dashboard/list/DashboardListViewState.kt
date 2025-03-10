package com.elpablo.fscslutsky.ui.dashboard.list

import com.elpablo.fscslutsky.domain.model.News

data class DashboardListViewState(
    val content: List<News> = emptyList(),
    val news: News = News(),
    val showBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
)
