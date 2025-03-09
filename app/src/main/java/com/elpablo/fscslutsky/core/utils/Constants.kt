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

data class VKAPI(
    val scheme: String = "https",
    val host: String = "api.vk.com",
    val version: String = "5.199",
    val domain: String = "fsc_slutsky_vlg",
    val count: Int = 20,
    val newsFeed: String = "/method/wall.get"
)