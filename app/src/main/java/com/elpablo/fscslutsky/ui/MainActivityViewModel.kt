package com.elpablo.fscslutsky.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.elpablo.fscslutsky.core.navigation.Graph
import com.elpablo.fscslutsky.core.navigation.Screen
import com.elpablo.fscslutsky.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(repository: AuthRepository) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Graph.AUTHORIZATION.route)
    val startDestination: State<String> = _startDestination

    init {
        if (repository.isUserAuth()) {
            _isLoading.value = false
            _startDestination.value = Graph.MAIN.route
        } else {
            _isLoading.value = false
            _startDestination.value = Graph.AUTHORIZATION.route
        }
    }
}