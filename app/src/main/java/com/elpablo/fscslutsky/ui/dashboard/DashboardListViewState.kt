package com.elpablo.fscslutsky.ui.dashboard

import com.elpablo.fscslutsky.data.model.News

data class DashboardListViewState(
    val content: List<News> = emptyList(),
    val news: News = News(),
    val showBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
)
