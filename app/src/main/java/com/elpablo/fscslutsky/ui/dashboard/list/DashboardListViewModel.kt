package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.domain.repository.NewsRepository
import com.vk.api.sdk.VK
import com.vk.dto.common.id.UserId
import com.vk.id.vksdksupport.withVKIDToken
import com.vk.sdk.api.wall.WallService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardListViewModel @Inject constructor(private val repository: NewsRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(DashboardListViewState())
    val viewState: StateFlow<DashboardListViewState> = _viewState

    init {
        _viewState.value = _viewState.value.copy(isLoading = true)
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            val request = VK.executeSync(
                WallService().wallGet(ownerId = UserId(-191885529), count = 20).withVKIDToken()
            )
            _viewState.update { state ->
                state.copy(posts = request.items, isLoading = false)
            }
        }
    }

    private fun getNews() = viewModelScope.launch(Dispatchers.IO) {
        repository.getFirstPartNews().collect { result ->
            when (result) {
                is Response.Loading -> {
                    _viewState.value = _viewState.value.copy(isLoading = true)
                }

                is Response.Failure -> {
                    _viewState.value = _viewState.value.copy(
                        isLoading = false,
                        isError = true,
                        error = result.e?.localizedMessage ?: "Unexpected Error"
                    )
                }

                is Response.Success -> {
                    result.data?.let { data ->
                        _viewState.value = _viewState.value.copy(isLoading = false, content = data)
                    }
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
                    _viewState.value = _viewState.value.copy(
                        isLoading = false,
                        isError = true,
                        error = result.e?.localizedMessage ?: "Unexpected Error"
                    )
                }

                is Response.Success -> {
                    result.data?.let { data ->
                        _viewState.value = _viewState.value.copy(
                            isLoading = false,
                            content = _viewState.value.content + data
                        )
                    }
                }
            }
        }
    }
}