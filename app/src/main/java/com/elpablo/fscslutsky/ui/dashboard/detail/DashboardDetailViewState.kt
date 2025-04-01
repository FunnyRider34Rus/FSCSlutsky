package com.elpablo.fscslutsky.ui.dashboard.detail

import com.vk.sdk.api.wall.dto.WallWallItemDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentDto

data class DashboardDetailViewState(
    val content: WallWallItemDto? = null,
    val attachments: MutableList<WallWallpostAttachmentDto?> = mutableListOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = ""
)