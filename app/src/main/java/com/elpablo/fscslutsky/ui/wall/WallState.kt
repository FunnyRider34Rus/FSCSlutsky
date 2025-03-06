package com.elpablo.fscslutsky.ui.wall

import com.elpablo.fscslutsky.data.model.Post

data class WallState(
    val posts: List<Post> = emptyList(),
    val videoURL: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
