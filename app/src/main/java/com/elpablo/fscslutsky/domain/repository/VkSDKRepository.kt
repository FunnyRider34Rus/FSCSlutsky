package com.elpablo.fscslutsky.domain.repository

import com.elpablo.fscslutsky.core.utils.Response
import com.vk.sdk.api.video.dto.VideoVideoFullDto
import com.vk.sdk.api.wall.dto.WallWallItemDto
import kotlinx.coroutines.flow.Flow

interface VkSDKRepository {
    suspend fun getVKWallPosts(offset: Int): Flow<Response<List<WallWallItemDto.WallWallpostFullDto>>>
    suspend fun getVKWallPostByID(id: Int?): Flow<Response<WallWallItemDto.WallWallpostFullDto>>
    suspend fun getVKWallVideoById(id: Int?): Flow<Response<VideoVideoFullDto>>
}