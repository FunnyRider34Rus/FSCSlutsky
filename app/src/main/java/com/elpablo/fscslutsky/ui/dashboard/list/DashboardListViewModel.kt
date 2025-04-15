package com.elpablo.fscslutsky.ui.dashboard.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.core.utils.VK_WALL_COUNT
import com.elpablo.fscslutsky.domain.repository.MatchesRepository
import com.elpablo.fscslutsky.domain.repository.VkSDKRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardListViewModel @Inject constructor(
    private val vkRepository: VkSDKRepository,
    private val matchesRepository: MatchesRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(DashboardListViewState())
    val viewState: StateFlow<DashboardListViewState> = _viewState

    private var offset = VK_WALL_COUNT

    init {
        initialData()
    }

    private fun initialData() {
        getPosts()
        getMatches()
        getClubs()
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
                                error = result.e?.localizedMessage ?: "Unexpected Error",
                                isPostLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    private fun getVideoByID(id: Int?, indexOfPost: Int, indexOfAttachment: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            var temp = _viewState.value.posts
            vkRepository.getVKWallVideoById(
                id = id,
                ownerId = temp[indexOfPost].attachments?.get(indexOfAttachment)?.video?.ownerId
            ).collect { result ->
                when (result) {
                    is Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isVideoLoading = true)
                        }
                    }

                    is Response.Success -> {
                        temp[indexOfPost].attachments?.get(indexOfAttachment)?.video = result.data
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
                                error = result.e?.localizedMessage ?: "Unexpected Error",
                                isVideoLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: DashboardListEvent) {
        when (event) {
            is DashboardListEvent.NextRequest -> {
                getPosts()
            }

            is DashboardListEvent.GetVideoByID -> {
                getVideoByID(event.id, event.indexOfPost, event.indexOfAttachment)
            }
        }
    }

    private fun getMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            matchesRepository.getUpcomingMatches().collect { result ->
                when (result) {
                    Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isPostLoading = true)
                        }
                    }

                    is Response.Success -> {
                        if (result.data != null) {
                            _viewState.update { state ->
                                state.copy(
                                    matches = result.data,
                                    isPostLoading = false
                                )
                            }
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

    private fun getClubs() {
        viewModelScope.launch(Dispatchers.IO) {
            matchesRepository.getClubs().collect { result ->
                when (result) {
                    Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isPostLoading = true)
                        }
                    }

                    is Response.Success -> {
                        if (result.data != null) {
                            _viewState.update { state ->
                                state.copy(
                                    clubs = result.data,
                                    isPostLoading = false
                                )
                            }
                            getAwayClub()
                            getHomeClub()
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

    private fun getHomeClub() {
        for (club in _viewState.value.clubs) {
            if (_viewState.value.matches.isNotEmpty()) {
                if (club.id == _viewState.value.matches.first().homeId) {
                    _viewState.update { state ->
                        state.copy(
                            homeClub = club
                        )
                    }
                }
            }
        }
    }

    private fun getAwayClub() {
        for (club in _viewState.value.clubs) {
            if (_viewState.value.matches.isNotEmpty()) {
                if (club.id == _viewState.value.matches.first().awayId) {
                    _viewState.update { state ->
                        state.copy(
                            awayClub = club
                        )
                    }
                }
            }
        }
    }
}