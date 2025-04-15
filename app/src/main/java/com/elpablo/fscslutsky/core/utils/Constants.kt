package com.elpablo.fscslutsky.core.utils

//Утилитарный класс для работы с сетевыми запросами
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
const val FIRESTORE_NODE_MATCHES = "Matches"

const val FIRESTORE_NODE_CLUBS = "Clubs"

const val FIRESTORE_NODE_NEWS_TIMESTAMP = "date"

//Лимит на количество item в списке
const val VK_WALL_COUNT = 10
const val VK_ID_COMMUNITY = -191885529L