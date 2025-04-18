package com.elpablo.fscslutsky.ui.profile

import com.vk.id.VKIDUser

data class ProfileViewState(
    val user: VKIDUser? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = ""
)