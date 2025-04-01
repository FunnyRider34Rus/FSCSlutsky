package com.elpablo.fscslutsky.data.repository

import android.util.Log
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.VK_ID_COMMUNITY
import com.elpablo.fscslutsky.domain.repository.VkWallRepository
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.dto.common.id.UserId
import com.vk.id.vksdksupport.withVKIDToken
import com.vk.sdk.api.video.VideoService
import com.vk.sdk.api.video.dto.VideoGetResponseDto
import com.vk.sdk.api.wall.WallService
import com.vk.sdk.api.wall.dto.WallGetResponseDto
import com.vk.sdk.api.wall.dto.WallWallItemDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.collections.first

class VkWallRepositoryImpl @Inject constructor() : VkWallRepository {
    override suspend fun getVKWallPosts(offset: Int): Flow<Response<List<WallWallItemDto>>> = callbackFlow {
        trySend(Response.Loading)
        val callback = object : VKApiCallback<WallGetResponseDto> {
            override fun success(result: WallGetResponseDto) {
                trySend(Response.Success(result.items))
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
        awaitClose {  }
    }

    override suspend fun getVKWallPostByID(id: Int?): Flow<Response<WallWallItemDto>> = callbackFlow {
        trySend(Response.Loading)
        val callback = object : VKApiCallback<List<WallWallItemDto>> {
            override fun success(result: List<WallWallItemDto>) {
                trySend(Response.Success(result.first()))
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
        awaitClose {  }
    }

    override suspend fun getVKWallVideoById(id: Int?): Flow<Response<VideoGetResponseDto>> = callbackFlow {
        trySend(Response.Loading)
        val callback = object : VKApiCallback<VideoGetResponseDto> {
            override fun success(result: VideoGetResponseDto) {
                trySend(Response.Success(result))
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