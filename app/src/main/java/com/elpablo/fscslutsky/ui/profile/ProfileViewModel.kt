package com.elpablo.fscslutsky.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.domain.model.User
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
class ProfileViewModel @Inject constructor(private val currentUser: User?) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileViewState())
    val uiState: StateFlow<ProfileViewState> get() = _uiState

    init {
        _uiState.update { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            VKID.instance.getUserData(
                callback = object : VKIDGetUserCallback {
                    override fun onSuccess(user: VKIDUser) {
                        currentUser?.copy(
                            accessToken = VKID.instance.accessToken?.token,
                            firstName = user.firstName,
                            lastName = user.lastName,
                            photoURL = user.photo200
                        )
                        _uiState.update { state ->
                            state.copy(
                                user = user,
                                isLoading = false
                            )
                        }
                    }

                    override fun onFail(fail: VKIDGetUserFail) {
                        Log.d("VKID", "GetUserInfoFailed: $fail.description")
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                isError = true,
                                error = fail.description
                            )
                        }
                    }
                }
            )
        }
    }
}