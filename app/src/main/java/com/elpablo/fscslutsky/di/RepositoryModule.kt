package com.elpablo.fscslutsky.di

import android.content.Context
import com.elpablo.fscslutsky.data.repository.ConnectivityObserverImpl
import com.elpablo.fscslutsky.data.repository.NewsRepositoryImpl
import com.elpablo.fscslutsky.domain.model.User
import com.elpablo.fscslutsky.domain.repository.ConnectivityObserver
import com.elpablo.fscslutsky.domain.repository.NewsRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsRepository(firestore: FirebaseFirestore): NewsRepository = NewsRepositoryImpl(firestore = firestore)

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver = ConnectivityObserverImpl(context = context)

    @Provides
    @Singleton
    fun provideCurrentUser(): User? = User()
}