package com.elpablo.fscslutsky.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.elpablo.fscslutsky.core.utils.DATASTORE_PREFERENCES_USER_PREFERENCES
import com.elpablo.fscslutsky.domain.model.User
import com.elpablo.fscslutsky.domain.repoitory.UserRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) : UserRepository {

    private val Context.dataStore by preferencesDataStore(DATASTORE_PREFERENCES_USER_PREFERENCES)
    private val gson = Gson()

    private object Keys {
        val USER = stringPreferencesKey("user")
    }

    override suspend fun saveUser(user: User) {
            context.dataStore.edit { preferences ->
                preferences[Keys.USER] = gson.toJson(user)
            }
    }

    override suspend fun getUser(): User? {
        return context.dataStore.data.map { preferences ->
            preferences[Keys.USER]?.let { json ->
                gson.fromJson(json, User::class.java)
            }
        }
            .firstOrNull()
    }

    override suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.remove(Keys.USER)
        }
    }
}