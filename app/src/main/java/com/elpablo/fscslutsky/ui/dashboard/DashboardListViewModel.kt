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
class DashboardListViewModel @Inject constructor(private val repository: NewsRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(DashboardListViewState())
    val viewState: StateFlow<DashboardListViewState> = _viewState

    init {
        _viewState.value = _viewState.value.copy(isLoading = true)
        getNews()
    }

    private fun getNews() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFirstPartNews().collect { result ->
            when (result) {
                is Response.Loading -> {
                    _viewState.value = _viewState.value.copy(isLoading = true)
                }

                is Response.Failure -> {
                    _viewState.value = _viewState.value.copy(isLoading = false, isError = true, error = result.e?.localizedMessage ?: "Unexpected Error")
                }

                is Response.Success -> {
                    result.data?.let { data -> _viewState.value = _viewState.value.copy(isLoading = false, content = data) }
                }
            }
        }
    }

    fun onEvent(event: DashboardListEvent) = viewModelScope.launch(Dispatchers.IO) {
        when (event) {
            is DashboardListEvent.OnCardClick -> {
                _viewState.value = _viewState.value.copy(news = event.news, showBottomSheet = true)
            }

            is DashboardListEvent.NextRequest -> {
                getNextNews()
            }

            is DashboardListEvent.BottomSheetDismiss -> {
                _viewState.value = _viewState.value.copy(showBottomSheet = false)
            }
        }
    }

    private fun getNextNews() = viewModelScope.launch(Dispatchers.IO) {
        repository.getNextPartNews().collect { result ->
            when (result) {
                is Response.Loading -> {
                    _viewState.value = _viewState.value.copy(isLoading = true)
                }

                is Response.Failure -> {
                    _viewState.value = _viewState.value.copy(isLoading = false, isError = true, error = result.e?.localizedMessage ?: "Unexpected Error")
                }

                is Response.Success -> {
                    result.data?.let { data -> _viewState.value = _viewState.value.copy(isLoading = false, content = _viewState.value.content + data) }
                }
            }
        }
    }
}