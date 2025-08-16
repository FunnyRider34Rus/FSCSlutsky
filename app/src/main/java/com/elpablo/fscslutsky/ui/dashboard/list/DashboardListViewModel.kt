package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.VK_WALL_COUNT
import com.elpablo.fscslutsky.domain.repository.VkSDKRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardListViewModel @Inject constructor(
    private val vkRepository: VkSDKRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(DashboardListViewState())
    val viewState: StateFlow<DashboardListViewState>
        get() = _viewState.asStateFlow()

    private var offset = VK_WALL_COUNT

    init {
        initialData()
    }

    private fun initialData() {
        getPosts()
    }

    fun uiEvent(event: DashboardListEvent) {
        when (event) {
            is DashboardListEvent.NextRequest -> {
                getPosts()
            }

            is DashboardListEvent.GetVideoByID -> {
                getVideoByID(event.id, event.indexOfPost, event.indexOfAttachment)
            }
        }
    }

    private fun getPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            vkRepository.getVKWallPosts(offset).collect { result ->
                when (result) {
                    is Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isPostLoading = true)
                        }
                    }

                    is Response.Success -> {
                        result.data?.let { data ->
                            _viewState.update { state ->
                                state.copy(
                                    posts = result.data,
                                    isPostLoading = false
                                )
                            }
                        }
                        offset += VK_WALL_COUNT
                    }

                    is Response.Failure -> {
                        _viewState.update { state ->
                            state.copy(
                                isError = true,
                                error = result.e?.localizedMessage,
                                isPostLoading = true
                            )
                        }
                    }
                }
            }
        }
    }


    private fun getVideoByID(id: Int?, indexOfPost: Int?, indexOfAttachment: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val temp = _viewState.value.posts
            vkRepository.getVKWallVideoById(
                id = id,
                ownerId = indexOfPost?.let { index -> temp?.get(index) }?.attachments?.get(
                    indexOfAttachment
                )?.video?.ownerId
            ).collect { result ->
                when (result) {
                    is Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isVideoLoading = true)
                        }
                    }

                    is Response.Success -> {
                        indexOfPost?.let { index -> temp?.get(index) }?.attachments?.get(
                            indexOfAttachment
                        )?.video = result.data
                        _viewState.update { state ->
                            state.copy(
                                posts = temp,
                                isVideoLoading = false
                            )
                        }
                    }

                    is Response.Failure -> {
                        _viewState.update { state ->
                            state.copy(
                                isError = true,
                                error = result.e?.localizedMessage
                            )
                        }
                    }
                }
            }
        }
    }
}