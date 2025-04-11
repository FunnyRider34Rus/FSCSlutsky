package com.elpablo.fscslutsky.domain.model

import com.vk.sdk.api.base.dto.BaseBoolIntDto
import com.vk.sdk.api.photos.dto.PhotosPhotoDto
import com.vk.sdk.api.photos.dto.PhotosPhotoSizesDto
import com.vk.sdk.api.video.dto.VideoVideoFullDto
import com.vk.sdk.api.video.dto.VideoVideoImageDto
import com.vk.sdk.api.wall.dto.WallWallItemDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentTypeDto

data class VkWall(
    var id: Int? = null,
    var isPinned: Boolean? = null,
    var date: Int? = null,
    var text: String? = null,
    var attachments: List<VkWallAttachment>? = null
)

data class VkWallAttachment(
    var type: AttachmentType? = null,
    var photo: VkWallPhoto? = null,
    var video: VkWallVideo? = null
)

data class VkWallPhoto(
    var id: Int?,
    var sizes: List<VkWallPhotoSize>? = null
)

data class VkWallPhotoSize(
    var type: String?,
    var url: String?
)

data class VkWallVideo(
    var id: Int?,
    var ownerId: Long?,
    var image: List<VkWallVideoImage>?,
    var player: String?
)

data class VkWallVideoImage(
    var url: String?,
)

enum class AttachmentType {
    PHOTO, VIDEO
}

//Extentions для преобразования DTO в модель
fun WallWallItemDto.WallWallpostFullDto.toVkWall(): VkWall {
    return VkWall(
        id = this.id,
        isPinned = when (this.isPinned) {
            BaseBoolIntDto.NO -> false
            BaseBoolIntDto.YES -> true
            null -> null
        },
        date = this.date,
        text = this.text,
        attachments = this.attachments?.map { it.toVkWallAttachment() }
    )
}

fun WallWallpostAttachmentDto.toVkWallAttachment(): VkWallAttachment {
    return VkWallAttachment(
        type = when (this.type) {
            WallWallpostAttachmentTypeDto.PHOTO -> AttachmentType.PHOTO
            WallWallpostAttachmentTypeDto.VIDEO -> AttachmentType.VIDEO
            else -> null
        },
        photo = this.photo?.toVkWallPhoto(),
        video = this.video?.toVkWallVideo()
    )
}

fun PhotosPhotoDto.toVkWallPhoto(): VkWallPhoto {
    return VkWallPhoto(
        id = this.id,
        sizes = this.sizes?.map { it.toVkWallPhotoSize() }
    )
}

fun PhotosPhotoSizesDto.toVkWallPhotoSize(): VkWallPhotoSize {
    return VkWallPhotoSize(
        type = this.type.value,
        url = this.url
    )
}

fun VideoVideoFullDto.toVkWallVideo(): VkWallVideo {
    return VkWallVideo(
        id = this.id,
        ownerId = this.ownerId?.value,
        image = this.image?.map { it.toVkWallVideoImage() },
        player = this.player
    )
}

fun VideoVideoImageDto.toVkWallVideoImage(): VkWallVideoImage {
    return VkWallVideoImage(
        url = this.url
    )
}