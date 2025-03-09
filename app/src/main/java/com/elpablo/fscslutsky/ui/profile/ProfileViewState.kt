package com.elpablo.fscslutsky.ui.profile

import com.elpablo.fscslutsky.domain.model.User

data class ProfileViewState(
    val user: User? = null,
    val isLoggedIn: Boolean = false,
    val isError: Boolean = false,
    val error: String = "",
)