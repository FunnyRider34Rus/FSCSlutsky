package com.elpablo.fscslutsky.domain.repoitory

import com.elpablo.fscslutsky.domain.model.User

interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun clearUser()
}