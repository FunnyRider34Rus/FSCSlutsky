package com.elpablo.fscslutsky.domain.model

data class User(
    var id: String? = null,
    var accessToken: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var photoURL: String? = null,
)