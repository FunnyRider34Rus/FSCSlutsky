package com.elpablo.fscslutsky.domain.repository

import com.elpablo.fscslutsky.core.utils.Response
import com.vk.sdk.api.video.dto.VideoGetResponseDto
import com.vk.sdk.api.wall.dto.WallWallItemDto
import kotlinx.coroutines.flow.Flow

interface VkWallRepository {
    suspend fun getVKWallPosts(offset: Int): Flow<Response<List<WallWallItemDto>>>
    suspend fun getVKWallPostByID(id: Int?): Flow<Response<WallWallItemDto>>
    suspend fun getVKWallVideoById(id: Int?): Flow<Response<VideoGetResponseDto>>
}