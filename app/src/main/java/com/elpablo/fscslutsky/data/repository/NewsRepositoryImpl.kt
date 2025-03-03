package com.elpablo.fscslutsky.data.repository

import com.elpablo.fscslutsky.core.utils.FIRESTORE_LIMIT_QUERY
import com.elpablo.fscslutsky.core.utils.FIRESTORE_NODE_NEWS
import com.elpablo.fscslutsky.core.utils.FIRESTORE_NODE_NEWS_TIMESTAMP
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.Response.Failure
import com.elpablo.fscslutsky.core.utils.Response.Success
import com.elpablo.fscslutsky.data.model.News
import com.elpablo.fscslutsky.domain.repoitory.NewsRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    NewsRepository {
    lateinit var lastSnapshot: DocumentSnapshot
    override suspend fun getFirstPartNews(): Flow<Response<List<News>>> = flow {
        emit(Response.Loading)
        try {
            val query = firestore.collection(FIRESTORE_NODE_NEWS)
                .orderBy(FIRESTORE_NODE_NEWS_TIMESTAMP, Query.Direction.DESCENDING)
                .limit(FIRESTORE_LIMIT_QUERY)
                .get()
                .addOnSuccessListener { documentSnapshots ->
                    if (documentSnapshots.size() > 0) lastSnapshot = documentSnapshots.documents[documentSnapshots.size() - 1]
                }.await().toObjects(News::class.java)
            if (query.isNotEmpty()) emit(Success(query))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override suspend fun getNextPartNews(): Flow<Response<List<News>>> = flow {
        emit(Response.Loading)
        try {
            val nextQuery = firestore.collection(FIRESTORE_NODE_NEWS)
                .orderBy(FIRESTORE_NODE_NEWS_TIMESTAMP, Query.Direction.DESCENDING)
                .startAfter(lastSnapshot)
                .limit(FIRESTORE_LIMIT_QUERY)
                .get()
                .addOnSuccessListener { documentSnapshots ->
                    if (documentSnapshots.size() > 0) lastSnapshot = documentSnapshots.documents[documentSnapshots.size() - 1]
                }
                .await()
                .toObjects(News::class.java)
            if (nextQuery.isNotEmpty()) emit(Success(nextQuery))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    override suspend fun getNewsByID(id: String?): Flow<Response<News>>  = callbackFlow {
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