package com.elpablo.fscslutsky.data.model

data class User(
    var id: Long? = null,
    var accessToken: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var photoURL: String? = null,
)
