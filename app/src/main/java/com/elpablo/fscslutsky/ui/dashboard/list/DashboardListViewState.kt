package com.elpablo.fscslutsky.ui.dashboard.list

import com.elpablo.fscslutsky.domain.model.VkWall

data class DashboardListViewState(
    val posts: List<VkWall> = emptyList(),
    val isPostLoading: Boolean = false,
    val isVideoLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
)
