package com.elpablo.fscslutsky.data.repository

import com.elpablo.fscslutsky.core.utils.FIRESTORE_NODE_NEWS
import com.elpablo.fscslutsky.core.utils.FIRESTORE_NODE_NEWS_TIMESTAMP
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.Response.Failure
import com.elpablo.fscslutsky.core.utils.Response.Success
import com.elpablo.fscslutsky.data.model.News
import com.elpablo.fscslutsky.domain.repoitory.NewsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor (private val firestore: FirebaseFirestore): NewsRepository {
    override suspend fun getNews(): Flow<Response<List<News>>> = callbackFlow {
        val listener = firestore
            .collection(FIRESTORE_NODE_NEWS)
            .orderBy(FIRESTORE_NODE_NEWS_TIMESTAMP, Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                val response = if (value != null) {
                    val content = value.toObjects(News::class.java)
                    Success(content)
                } else {
                    Failure(error)
                }
                trySend(response)
            }
        awaitClose { listener.remove() }
    }

    override fun getNewsByID(id: String?): Flow<Response<News>> = callbackFlow {
        val docRef = id?.let { id -> firestore.collection(FIRESTORE_NODE_NEWS).document(id) }
        docRef?.get()?.addOnCompleteListener { task ->
            val response = if (task.isSuccessful) {
                Success(task.result.toObject(News::class.java))
            } else {
                Failure(task.exception)
            }
            trySend(response)
        }
        awaitClose { }
    }
}