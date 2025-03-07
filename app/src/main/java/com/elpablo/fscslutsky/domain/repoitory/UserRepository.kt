package com.elpablo.fscslutsky.domain.repoitory

import com.elpablo.fscslutsky.data.model.User

interface UserRepository {
    fun getCurrentUser(): User
    fun updateCurrentUser(
        id: Long? = null,
        accessToken: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        photoURL: String? = null
    )
}