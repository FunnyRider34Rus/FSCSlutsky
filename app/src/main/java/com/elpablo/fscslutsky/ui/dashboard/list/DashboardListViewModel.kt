package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.VK_WALL_COUNT
import com.elpablo.fscslutsky.domain.repository.VkWallRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardListViewModel @Inject constructor(private val repository: VkWallRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(DashboardListViewState())
    val viewState: StateFlow<DashboardListViewState> = _viewState

    private var offset = VK_WALL_COUNT

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getVKWallPosts(offset).collect { result ->
                when (result) {
                    is Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is Response.Success -> {
                        result.data?.let { data ->
                            _viewState.update { state ->
                                state.copy(posts = data, isLoading = false)
                            }
                            offset += VK_WALL_COUNT
                        }
                    }

                    is Response.Failure -> {
                        _viewState.update { state ->
                            state.copy(
                                isError = true,
                                error = result.e?.localizedMessage ?: "Unexpected Error",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: DashboardListEvent) = viewModelScope.launch(Dispatchers.IO) {
        when (event) {
            is DashboardListEvent.NextRequest -> {
                getPosts()
            }
        }
    }
}