package com.elpablo.fscslutsky.ui.wall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.networking.VkApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallViewModel @Inject constructor(
    private val apiVK: VkApiService
) : ViewModel() {

    private val _state = MutableStateFlow(WallState())
    val state: StateFlow<WallState> get() = _state

    fun onEvent(event: WallEvent) {
        when (event) {
            is WallEvent.LoadPosts -> loadPosts()
            is WallEvent.GetVideo -> getVideoUrl(
                videoId = event.videoId,
                accessKey = event.accessKey
            )
        }
    }

    private fun loadPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            try {
                val response =
                    apiVK.getWallPosts(accessToken = "v1")
                if (response.isSuccessful) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            posts = response.body()?.response?.items ?: emptyList()
                        )
                    }
                } else {
                    _state.update {
                        it.copy(error = "Error: ${response.message()}", isLoading = false)
                    }
                }
            } catch (error: Exception) {
                _state.update {
                    it.copy(error = "Exception: ${error.message}", isLoading = false)
                }
            }
        }
    }

    private fun getVideoUrl(videoId: Int?, accessKey: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            try {
                val response = apiVK.getVideo(accessToken = "", videoId = videoId, accessKey = accessKey)
                if (response.isSuccessful) {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                } else {
                    _state.update {
                        it.copy(error = "Error: ${response.message()}", isLoading = false)
                    }
                }
            } catch (error: Exception) {
                _state.update {
                    it.copy(error = "Exception: ${error.message}", isLoading = false)
                }
            }
        }
    }
}



