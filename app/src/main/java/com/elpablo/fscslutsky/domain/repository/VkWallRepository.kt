package com.elpablo.fscslutsky.domain.repository

import com.elpablo.fscslutsky.core.utils.Response
import com.vk.sdk.api.wall.dto.WallWallpostFullDto
import kotlinx.coroutines.flow.Flow

interface VkWallRepository {
    suspend fun getFirstPartNews(): Flow<Response<List<WallWallpostFullDto>>>
    suspend fun getNextPartNews(): Flow<Response<List<WallWallpostFullDto>>>
}