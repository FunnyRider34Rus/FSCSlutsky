package com.elpablo.fscslutsky.ui.profile

sealed class ProfileEvent {
    data class AuthSuccess(val accessToken: String): ProfileEvent()
    data class AuthFail(val error: String): ProfileEvent()
}