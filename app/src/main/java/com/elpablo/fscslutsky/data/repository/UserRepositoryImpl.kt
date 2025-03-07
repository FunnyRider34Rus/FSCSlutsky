package com.elpablo.fscslutsky.data.repository

import com.elpablo.fscslutsky.data.model.User
import com.elpablo.fscslutsky.domain.repoitory.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    private var currentUser = User()
    override fun getCurrentUser() = currentUser
    override fun updateCurrentUser(
        id: Long?,
        accessToken: String?,
        firstName: String?,
        lastName: String?,
        photoURL: String?
    ) {
        currentUser.apply {
            this.id = id
            this.accessToken = accessToken
            this.firstName = firstName
            this.lastName = lastName
            this.photoURL = photoURL
        }
    }
}