package com.elpablo.fscslutsky.domain

interface AuthRepository {
    fun isUserAuth(): Boolean
}