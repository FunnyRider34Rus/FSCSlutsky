package com.elpablo.fscslutsky.data.repository

import com.elpablo.fscslutsky.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override fun isUserAuth() = auth.currentUser != null
}