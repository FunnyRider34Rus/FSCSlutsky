package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardDetailViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private val _viewState = MutableStateFlow(DashboardDetailViewState())
    val viewState: StateFlow<DashboardDetailViewState> = _viewState

    fun fetchNews(id: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNewsByID(id).collect { result ->
                when (result) {
                    is Response.Loading -> {
                        _viewState.value = _viewState.value.copy(isLoading = true)
                    }

                    is Response.Failure -> {
                        _viewState.value = _viewState.value.copy(isError = true, error = result.e?.localizedMessage ?: "Unexpected Error")
                    }

                    is Response.Success -> {
                        _viewState.value = _viewState.value.copy(isLoading = false, content = result.data)
                    }
                }
            }
        }
    }
}