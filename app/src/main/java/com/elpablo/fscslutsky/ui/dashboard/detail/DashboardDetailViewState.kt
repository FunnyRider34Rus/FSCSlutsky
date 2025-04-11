package com.elpablo.fscslutsky.ui.dashboard.detail

import com.elpablo.fscslutsky.domain.model.VkWall

data class DashboardDetailViewState(
    val content: VkWall? = null,
    val isPostLoading: Boolean = false,
    val isVideoLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = ""
)