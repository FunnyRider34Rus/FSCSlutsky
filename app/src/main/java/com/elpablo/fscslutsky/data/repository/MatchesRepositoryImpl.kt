package com.elpablo.fscslutsky.data.repository

import com.elpablo.fscslutsky.core.utils.FIRESTORE_NODE_CLUBS
import com.elpablo.fscslutsky.core.utils.FIRESTORE_NODE_MATCHES
import com.elpablo.fscslutsky.core.utils.FIRESTORE_NODE_NEWS_TIMESTAMP
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.Response.Failure
import com.elpablo.fscslutsky.core.utils.Response.Loading
import com.elpablo.fscslutsky.core.utils.Response.Success
import com.elpablo.fscslutsky.domain.model.Club
import com.elpablo.fscslutsky.domain.model.Match
import com.elpablo.fscslutsky.domain.repository.MatchesRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    MatchesRepository {
    override suspend fun getClubs(): Flow<Response<List<Club>>> = flow {
        emit(Loading)
        val result = try {
            val query = firestore.collection(FIRESTORE_NODE_CLUBS).get().addOnSuccessListener {
            }.await().toObjects(Club::class.java)
            if (query.isNotEmpty()) {
                Success(query)
            } else {
                Success(emptyList())
            }
        } catch (e: Exception) {
            Failure(Exception(e.localizedMessage))
        }
        emit(result)
    }

    override suspend fun getUpcomingMatches(): Flow<Response<List<Match?>?>> = callbackFlow {
        trySend(Loading)
        val currentDate = Date()
        val listener = firestore.collection(FIRESTORE_NODE_MATCHES)
            .whereGreaterThanOrEqualTo(FIRESTORE_NODE_NEWS_TIMESTAMP, currentDate)
            .orderBy(FIRESTORE_NODE_NEWS_TIMESTAMP, Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) trySend(Failure(error))
                val response = if (snapshot != null) {
                    Success(snapshot.toObjects(Match::class.java))
                } else {
                    Failure(error)
                }
                trySend(response)
            }
        awaitClose { listener.remove() }
    }
}