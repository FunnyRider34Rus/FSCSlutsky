package com.elpablo.fscslutsky.ui.wall

sealed class WallEvent {
    object LoadPosts : WallEvent()
    data class GetVideo(
        val videoId: Int?,
        val accessKey: String?
    ) : WallEvent()
}