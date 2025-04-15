package com.elpablo.fscslutsky.domain.repository

import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.domain.model.Club
import com.elpablo.fscslutsky.domain.model.Match
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    suspend fun getClubs(): Flow<Response<List<Club>>>
    suspend fun getUpcomingMatches(): Flow<Response<List<Match>>>
}