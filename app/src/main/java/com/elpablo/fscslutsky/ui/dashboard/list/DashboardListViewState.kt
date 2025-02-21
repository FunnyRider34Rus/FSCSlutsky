package com.elpablo.fscslutsky.ui.dashboard.list

import com.elpablo.fscslutsky.data.model.News

data class DashboardListViewState(
    val content: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val isRequestLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
    val onNavigate: String = ""
)
