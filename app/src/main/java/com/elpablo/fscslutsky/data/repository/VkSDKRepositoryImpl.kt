package com.elpablo.fscslutsky.data.repository

import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.VK_ID_COMMUNITY
import com.elpablo.fscslutsky.domain.repository.VkSDKRepository
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.dto.common.id.UserId
import com.vk.id.vksdksupport.withVKIDToken
import com.vk.sdk.api.video.VideoService
import com.vk.sdk.api.video.dto.VideoGetResponseDto
import com.vk.sdk.api.video.dto.VideoVideoFullDto
import com.vk.sdk.api.wall.WallService
import com.vk.sdk.api.wall.dto.WallGetResponseDto
import com.vk.sdk.api.wall.dto.WallWallItemDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class VkSDKRepositoryImpl @Inject constructor() : VkSDKRepository {
    override suspend fun getVKWallPosts(offset: Int): Flow<Response<List<WallWallItemDto.WallWallpostFullDto>>> = callbackFlow {
        trySend(Response.Loading)
        val callback = object : VKApiCallback<WallGetResponseDto> {
            override fun success(result: WallGetResponseDto) {
                val response = mutableListOf<WallWallItemDto.WallWallpostFullDto>()
                result.items.forEach { item ->
                    when (item) {
                        is WallWallItemDto.WallWallpostFullDto -> {
                            response.add(item)
                        }
                        else -> {  }
                    }
                }
                trySend(Response.Success(response.toList()))
            }

            override fun fail(error: Exception) {
                trySend(Response.Failure(error))
            }

        }
        VK.execute(
            WallService()
                .wallGet(ownerId = UserId(VK_ID_COMMUNITY), count = offset, filter = "owner")
                .withVKIDToken(),
            callback
        )
        awaitClose { callback }
    }

    override suspend fun getVKWallPostByID(id: Int?): Flow<Response<WallWallItemDto.WallWallpostFullDto>> = callbackFlow {
        trySend(Response.Loading)
        val callback = object : VKApiCallback<List<WallWallItemDto>> {
            override fun success(result: List<WallWallItemDto>) {
                val response = result.first()
                when (response) {
                    is WallWallItemDto.WallWallpostFullDto -> {
                        trySend(Response.Success(response))
                    }
                    else -> {  }
                }
            }

            override fun fail(error: Exception) {
                trySend(Response.Failure(error))
            }
        }
        VK.execute(
            WallService()
                .wallGetById(posts = listOf(VK_ID_COMMUNITY.toString()+"_"+ id.toString()))
                .withVKIDToken(),
            callback
        )
        awaitClose { callback }
    }

    override suspend fun getVKWallVideoById(id: Int?): Flow<Response<VideoVideoFullDto>> = callbackFlow {
        trySend(Response.Loading)
        val callback = object : VKApiCallback<VideoGetResponseDto> {
            override fun success(result: VideoGetResponseDto) {
                val response = result.items.first()
                trySend(Response.Success(response))
            }
            override fun fail(error: Exception) {
                trySend(Response.Failure(error))
            }
        }
        VK.execute(
            VideoService()
                .videoGet(ownerId = UserId(VK_ID_COMMUNITY), videos = listOf(VK_ID_COMMUNITY.toString()+"_"+ id.toString()))
                .withVKIDToken(),
            callback
        )
        awaitClose {  }
    }
}