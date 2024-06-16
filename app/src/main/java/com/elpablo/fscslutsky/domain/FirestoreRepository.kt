package com.elpablo.fscslutsky.domain

import com.elpablo.fscslutsky.data.model.News

interface FirestoreRepository {
    fun getNews(): Array<News?>?
}