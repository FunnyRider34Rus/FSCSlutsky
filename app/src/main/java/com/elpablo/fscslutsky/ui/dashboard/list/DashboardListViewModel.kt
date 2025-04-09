package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.VK_WALL_COUNT
import com.elpablo.fscslutsky.domain.model.AttachmentType
import com.elpablo.fscslutsky.domain.model.VkWall
import com.elpablo.fscslutsky.domain.model.toVkWall
import com.elpablo.fscslutsky.domain.model.toVkWallVideo
import com.elpablo.fscslutsky.domain.repository.VkSDKRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardListViewModel @Inject constructor(private val repository: VkSDKRepository) :
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
                        var stateVkPosts: MutableList<VkWall> = mutableListOf()
                        result.data?.let { data ->
                            data.forEach { item ->
                                stateVkPosts.add(item.toVkWall())
                            }
                        }
                        viewModelScope.launch(Dispatchers.IO) {
                            stateVkPosts.forEachIndexed { indexOfPost, item ->
                                item.attachments?.forEachIndexed { indexOfAttachment, attachment ->
                                    when (attachment.type) {
                                        AttachmentType.VIDEO -> {
                                            repository.getVKWallVideoById(attachment.video?.id)
                                                .collect { result ->
                                                    when (result) {
                                                        is Response.Loading -> {
                                                            _viewState.update { state ->
                                                                state.copy(isLoading = true)
                                                            }
                                                        }

                                                        is Response.Success -> {
                                                            stateVkPosts[indexOfPost].attachments?.get(indexOfAttachment)?.video = result.data?.toVkWallVideo()
                                                            _viewState.update { state ->
                                                                state.copy(posts = stateVkPosts, isLoading = false)
                                                            }
                                                        }

                                                        is Response.Failure -> {
                                                            _viewState.update { state ->
                                                                state.copy(
                                                                    isError = true,
                                                                    error = result.e?.localizedMessage
                                                                        ?: "Unexpected Error",
                                                                    isLoading = false
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                        }

                                        AttachmentType.PHOTO -> {
                                            _viewState.update { state ->
                                                state.copy(posts = stateVkPosts, isLoading = false)
                                            }
                                        }
                                        null -> {  }
                                    }

                                }
                            }
                        }
                        offset += VK_WALL_COUNT
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

    private fun getVideoByID(id: Int?) = viewModelScope.launch(Dispatchers.IO) {
        repository.getVKWallVideoById(id).collect { result ->
            when (result) {
                is Response.Loading -> {
                    _viewState.update { state ->
                        state.copy(isLoading = true)
                    }
                }

                is Response.Success -> {

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

    fun onEvent(event: DashboardListEvent) = viewModelScope.launch(Dispatchers.IO) {
        when (event) {
            is DashboardListEvent.NextRequest -> {
                getPosts()
            }

            is DashboardListEvent.GetVideoByID -> {
                getVideoByID(event.id)
            }
        }
    }
}