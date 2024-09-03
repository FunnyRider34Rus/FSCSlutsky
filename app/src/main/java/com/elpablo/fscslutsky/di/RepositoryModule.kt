package com.elpablo.fscslutsky.di

import com.elpablo.fscslutsky.data.repository.NewsRepositoryImpl
import com.elpablo.fscslutsky.domain.repoitory.NewsRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsRepository(firestore: FirebaseFirestore): NewsRepository = NewsRepositoryImpl(firestore = firestore)
}