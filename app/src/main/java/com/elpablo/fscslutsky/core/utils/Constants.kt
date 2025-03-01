package com.elpablo.fscslutsky.core.utils

//Утилитарный класс для работы с FIREBASE
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

//Лимит на количество item в одном запросе
const val FIRESTORE_LIMIT_QUERY = 5L
