package com.elpablo.fscslutsky.domain.repoitory

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Boolean>
}