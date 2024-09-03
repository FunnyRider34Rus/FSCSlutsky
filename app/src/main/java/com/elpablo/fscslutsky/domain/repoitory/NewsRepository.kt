package com.elpablo.fscslutsky.domain.repoitory

import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.data.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<Response<List<News>>>
}