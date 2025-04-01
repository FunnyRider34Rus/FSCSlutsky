package com.elpablo.fscslutsky.ui.dashboard.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.toWallWallpostAttachmentDto
import com.elpablo.fscslutsky.domain.repository.VkWallRepository
import com.vk.sdk.api.wall.dto.WallWallItemDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentTypeDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardDetailViewModel @Inject constructor(private val repository: VkWallRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(DashboardDetailViewState())
    val viewState: StateFlow<DashboardDetailViewState> = _viewState

    fun getPostByID(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getVKWallPostByID(id).collect { result ->
                when (result) {
                    is Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is Response.Success -> {
                        _viewState.update { state ->
                            state.copy(content = result.data, isLoading = false)
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
}