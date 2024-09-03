package com.elpablo.fscslutsky.ui.dashboard

import com.elpablo.fscslutsky.data.model.News

data class DashboardViewState(
    val content: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = ""
)
