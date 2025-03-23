package com.elpablo.fscslutsky.ui.auth

sealed class AuthEvent {
    data object AuthSuccess: AuthEvent()
    data class AuthFail(val error: String): AuthEvent()
}