package com.elpablo.fscslutsky.core.networking
import com.elpablo.fscslutsky.data.model.Video
import com.elpablo.fscslutsky.data.model.WallResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApiService {
    @GET("wall.get")
    suspend fun getWallPosts(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: String = "-191885529",
        @Query("offset") offset: Int = 0,
        @Query("count") count: Int = 20,
        @Query("filter") filter: String = "owner",
        @Query("v") version: String = "5.131"
    ): Response<WallResponse>

    @GET("video.get")
    suspend fun getVideo(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: String = "-191885529",
        @Query("video_id") videoId: Int? = null,
        @Query("access_key") accessKey: String? = null,
        @Query("v") version: String = "5.131"
    ): Response<Video>
}