package com.elpablo.fscslutsky.ui.matches

import com.elpablo.fscslutsky.domain.model.FullMatchInfo

data class MatchesViewState(
    val matches: List<FullMatchInfo>? = null,
    val index: Int = 0,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
)