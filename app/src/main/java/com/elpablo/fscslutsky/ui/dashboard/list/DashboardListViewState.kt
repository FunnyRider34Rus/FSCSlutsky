package com.elpablo.fscslutsky.ui.dashboard.list

import com.elpablo.fscslutsky.domain.model.Club
import com.elpablo.fscslutsky.domain.model.Match
import com.elpablo.fscslutsky.domain.model.VkWall

data class DashboardListViewState(
    val posts: List<VkWall>? = null,
    val matches: List<Match?>? = null,
    val clubs: List<Club>? = null,
    val homeClub: Club? = null,
    val awayClub: Club? = null,
    val isPostLoading: Boolean = false,
    val isVideoLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
)
