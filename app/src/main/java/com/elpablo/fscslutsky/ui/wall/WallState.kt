package com.elpablo.fscslutsky.ui.wall

import com.elpablo.fscslutsky.domain.model.Post

data class WallState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
