package com.elpablo.fscslutsky.ui.dashboard.detail

import com.elpablo.fscslutsky.data.model.News

data class DashboardDetailViewState(
    val content: News? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = ""
)