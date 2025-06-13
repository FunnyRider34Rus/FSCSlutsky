package com.elpablo.fscslutsky.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(AuthViewState())
    val uiState: StateFlow<AuthViewState> get() = _uiState.asStateFlow()

    fun onEvent(event: AuthEvent) = viewModelScope.launch(Dispatchers.IO) {
        when (event) {
            is AuthEvent.AuthSuccess -> {
                _uiState.update { it.copy(isLoading = false, isLoggedIn = true) }
            }

            is AuthEvent.AuthFail -> {
                _uiState.update { it.copy(isLoading = false, isError = true, error = event.error) }
            }
        }
    }
}