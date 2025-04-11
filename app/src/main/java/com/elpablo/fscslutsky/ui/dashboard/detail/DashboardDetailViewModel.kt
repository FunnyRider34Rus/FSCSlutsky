package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.domain.model.toVkWall
import com.elpablo.fscslutsky.domain.repository.VkSDKRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardDetailViewModel @Inject constructor(private val repository: VkSDKRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(DashboardDetailViewState())
    val viewState: StateFlow<DashboardDetailViewState> = _viewState

    fun getPostByID(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getVKWallPostByID(id).collect { result ->
                when (result) {
                    is Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isPostLoading = true)
                        }
                    }

                    is Response.Success -> {
                        _viewState.update { state ->
                            state.copy(content = result.data?.toVkWall(), isPostLoading = false)
                        }
                    }

                    is Response.Failure -> {
                        _viewState.update { state ->
                            state.copy(
                                isError = true,
                                error = result.e?.localizedMessage ?: "Unexpected Error",
                                isPostLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getVideo(id: Int?, indexOfAttachment: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var temp = _viewState.value.content
            repository.getVKWallVideoById(id, temp?.attachments?.get(indexOfAttachment)?.video?.ownerId).collect { result ->
                when (result) {
                    is Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isVideoLoading = true)
                        }
                    }

                    is Response.Success -> {
                        temp?.attachments?.get(indexOfAttachment)?.video = result.data
                        _viewState.update { state ->
                            state.copy(
                                content = temp,
                                isVideoLoading = false
                            )
                        }
                    }

                    is Response.Failure -> {
                        _viewState.update { state ->
                            state.copy(
                                isError = true,
                                error = result.e?.localizedMessage ?: "Unexpected Error",
                                isVideoLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: DashboardDetailEvent) {
        when (event) {
            is DashboardDetailEvent.GetVideoByID -> {
                getVideo(event.id, event.indexOfAttachment)
            }

            is DashboardDetailEvent.GetPostByID -> {
                getPostByID(event.id)
            }
        }
    }
}