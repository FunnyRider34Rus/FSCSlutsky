package com.elpablo.fscslutsky.domain.model

data class WallResponse(
    val response: WallPosts
)

data class WallPosts(
    val count: Int,
    val items: List<Post>
)

data class Post(
    val id: Int = 0,
    val fromId: Int = 0,
    val ownerId: Int = 0,
    val date: Long = 0L,
    val text: String = "",
    val attachments: List<Attachment>? = null
)

data class Attachment(
    val type: String,
    val photo: Photo? = null,
    val video: Video? = null
)

data class Photo(
    val id: Int,
    val ownerId: Int,
    val sizes: List<PhotoSize>
)

data class PhotoSize(
    val type: String,
    val url: String
)

data class Video(
    val id: Int,
    val ownerId: Int,
    val accessKey: String,
    val image: List<VideoPhoto>
)

data class VideoResponse(
    val response: VideoItems
)

data class VideoPhoto(
    val url: String,
    val width: Int,
    val height: Int
)

data class VideoItems(
    val count: Int,
    val items: List<Video>
)

