package com.elpablo.fscslutsky.data.model

import com.google.firebase.Timestamp

data class News(
    val id: String? = null,
    val title: String? = null,
    val body: String? = null,
    val images: List<String?>? = null,
    val date: Timestamp? = null
)
