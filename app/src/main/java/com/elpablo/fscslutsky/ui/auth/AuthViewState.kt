package com.elpablo.fscslutsky.ui.auth

data class AuthViewState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String = ""
)
