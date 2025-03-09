package com.elpablo.fscslutsky.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.domain.model.User
import com.elpablo.fscslutsky.domain.repoitory.UserRepository
import com.vk.id.VKID
import com.vk.id.VKIDUser
import com.vk.id.refreshuser.VKIDGetUserCallback
import com.vk.id.refreshuser.VKIDGetUserFail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileViewState())
    val uiState: StateFlow<ProfileViewState> get() = _uiState

    init {
        Log.d("VKID", "Init: ${VKID.instance.accessToken?.token}")
        if (VKID.instance.accessToken?.token != null) {
            _uiState.update { it.copy(isLoggedIn = true) }
            viewModelScope.launch(Dispatchers.IO) {
                VKID.instance.getUserData(
                    callback = object : VKIDGetUserCallback {
                        override fun onSuccess(user: VKIDUser) {
                            viewModelScope.launch(Dispatchers.IO) {
                                val user = User(
                                    accessToken = VKID.instance.accessToken?.token,
                                    firstName = user.firstName,
                                    lastName = user.lastName,
                                    photoURL = user.photo200
                                )
                                repository.saveUser(
                                    user = user
                                )
                                _uiState.update { it.copy(user = user) }
                            }
                        }

                        override fun onFail(fail: VKIDGetUserFail) {
                            Log.d("VKID", "GetUserInfoFailed")
                            _uiState.update { it.copy(isError = true, error = fail.description) }
                        }
                    }
                )
            }
        }
    }

    fun onEvent(event: ProfileEvent) = viewModelScope.launch(Dispatchers.IO) {
        when (event) {
            is ProfileEvent.AuthSuccess -> {
                Log.d("VKID", "AuthSuccess: ${event.accessToken}")
                _uiState.update { it.copy(isLoggedIn = true) }
                VKID.instance.getUserData(
                    callback = object : VKIDGetUserCallback {
                        override fun onSuccess(user: VKIDUser) {
                            viewModelScope.launch(Dispatchers.IO) {
                                val user = User(
                                    accessToken = event.accessToken,
                                    firstName = user.firstName,
                                    lastName = user.lastName,
                                    photoURL = user.photo200
                                )
                                repository.saveUser(
                                    user = user
                                )
                                _uiState.update { it.copy(user = user) }
                            }
                        }

                        override fun onFail(fail: VKIDGetUserFail) {
                            Log.d("VKID", "GetUserInfoFailed")
                            _uiState.update { it.copy(isError = true, error = fail.description) }
                        }
                    }
                )
            }

            is ProfileEvent.AuthFail -> {
                Log.d("VKID", "AuthFailed")
                _uiState.update { it.copy(isError = true, error = event.error) }
            }
        }
    }
}