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

//DataStore Preferences
const val DATASTORE_PREFERENCES_USER_PREFERENCES = "user_prefs"

//Лимит на количество item в одном запросе
const val FIRESTORE_LIMIT_QUERY = 5L

//Лимит на количество item в списке
const val VK_WALL_COUNT = 20
const val VK_ID_COMMUNITY = -191885529L