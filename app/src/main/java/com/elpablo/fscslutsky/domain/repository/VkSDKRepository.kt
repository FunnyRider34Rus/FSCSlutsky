package com.elpablo.fscslutsky.domain.repository

import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.domain.model.VkWall
import com.elpablo.fscslutsky.domain.model.VkWallVideo
import com.vk.sdk.api.wall.dto.WallWallItemDto
import kotlinx.coroutines.flow.Flow

interface VkSDKRepository {
    suspend fun getVKWallPosts(offset: Int): Flow<Response<List<VkWall>>>
    suspend fun getVKWallPostByID(id: Int?): Flow<Response<WallWallItemDto.WallWallpostFullDto>>
    suspend fun getVKWallVideoById(id: Int?): Flow<Response<VkWallVideo>>
}