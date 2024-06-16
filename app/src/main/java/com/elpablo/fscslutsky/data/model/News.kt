package com.elpablo.fscslutsky.data.model

import com.google.firebase.Timestamp

data class News(
    val title: String?,
    val body: String?,
    val images: List<String?>?,
    val date: Timestamp?
)
