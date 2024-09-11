package com.elpablo.fscslutsky.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.elpablo.fscslutsky.core.navigation.Screen
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(): ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.DASHBOARDLIST.route)
    val startDestination: State<String> = _startDestination
}