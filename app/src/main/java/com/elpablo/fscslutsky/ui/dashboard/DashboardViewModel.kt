package com.elpablo.fscslutsky.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.domain.repoitory.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {
    private val _viewState = MutableStateFlow(DashboardViewState())
    val viewState: StateFlow<DashboardViewState> = _viewState

    init {
        getNews()
    }

    private fun getNews() = viewModelScope.launch(Dispatchers.IO) {
        repository.getNews().collect { result ->
            when (result) {
                is Response.Loading -> {
                    _viewState.value = _viewState.value.copy(isLoading = true)
                }
                is Response.Failure -> {
                    _viewState.value = _viewState.value.copy(isError = true)
                    _viewState.value = _viewState.value.copy(
                        error = result.e?.localizedMessage ?: "Unexpected Error"
                    )
                }
                is Response.Success -> {
                    _viewState.value = _viewState.value.copy(isLoading = false)
                    _viewState.value = _viewState.value.copy(isError = false)
                    _viewState.value = _viewState.value.copy(error = "")
                    _viewState.value = _viewState.value.copy(content = result.data ?: emptyList())
                }
            }
        }
    }

    fun onEvent(event: DashboardEvent) = viewModelScope.launch(Dispatchers.IO) {

    }
}