package com.elpablo.fscslutsky.di

import android.content.Context
import com.elpablo.fscslutsky.data.repository.ConnectivityObserverImpl
import com.elpablo.fscslutsky.data.repository.MatchesRepositoryImpl
import com.elpablo.fscslutsky.data.repository.VkSDKRepositoryImpl
import com.elpablo.fscslutsky.domain.model.User
import com.elpablo.fscslutsky.domain.repository.ConnectivityObserver
import com.elpablo.fscslutsky.domain.repository.MatchesRepository
import com.elpablo.fscslutsky.domain.repository.VkSDKRepository
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
    fun provideVkSDKRepository(): VkSDKRepository = VkSDKRepositoryImpl()

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver = ConnectivityObserverImpl(context = context)

    @Provides
    @Singleton
    fun provideCurrentUser(): User? = User()

    @Provides
    @Singleton
    fun provideMatchesRepository(firestore: FirebaseFirestore): MatchesRepository = MatchesRepositoryImpl(firestore = firestore)
}