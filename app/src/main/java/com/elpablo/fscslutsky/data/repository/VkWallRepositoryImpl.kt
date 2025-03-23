package com.elpablo.fscslutsky.data.repository

import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.domain.repository.VkWallRepository
import com.vk.sdk.api.wall.dto.WallWallpostFullDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VkWallRepositoryImpl @Inject constructor() : VkWallRepository {
    override suspend fun getFirstPartNews(): Flow<Response<List<WallWallpostFullDto>>> = flow {
        emit(Response.Loading)

    }

    override suspend fun getNextPartNews(): Flow<Response<List<WallWallpostFullDto>>> = flow {
        emit(Response.Loading)

    }
}