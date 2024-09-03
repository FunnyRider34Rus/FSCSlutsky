package com.elpablo.fscslutsky.core.utils

sealed class Response<out T> {
    data object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : Response<T>()

    data class Failure(
        val e: Exception?
    ) : Response<Nothing>()
}

//Firestore Collection References
const val FIRESTORE_NODE_NEWS = "News"

//Firestore Collection Fields
const val FIRESTORE_NODE_NEWS_TIMESTAMP = "date"
