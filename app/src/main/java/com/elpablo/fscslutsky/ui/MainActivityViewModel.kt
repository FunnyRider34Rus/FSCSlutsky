package com.elpablo.fscslutsky.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.elpablo.fscslutsky.core.navigation.Screen
import com.vk.api.sdk.VK
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    val isUserAuthenticated get() = VK.isLoggedIn()

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    val startDestination = Screen.WALL.route
}