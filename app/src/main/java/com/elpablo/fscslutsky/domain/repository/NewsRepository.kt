package com.elpablo.fscslutsky.domain.repository

import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getFirstPartNews(): Flow<Response<List<News>>>
    suspend fun getNextPartNews(): Flow<Response<List<News>>>
    suspend fun getNewsByID(id: String?): Flow<Response<News>>
}