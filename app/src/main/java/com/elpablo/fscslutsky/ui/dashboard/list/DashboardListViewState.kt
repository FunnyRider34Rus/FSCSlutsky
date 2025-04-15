package com.elpablo.fscslutsky.ui.dashboard.list

import com.elpablo.fscslutsky.domain.model.Club
import com.elpablo.fscslutsky.domain.model.Match
import com.elpablo.fscslutsky.domain.model.VkWall

data class DashboardListViewState(
    val posts: List<VkWall> = emptyList(),
    val matches: List<Match> = emptyList(),
    val clubs: List<Club> = emptyList(),
    val homeClub: Club = Club(),
    val awayClub: Club = Club(),
    val isPostLoading: Boolean = false,
    val isVideoLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
)
