package com.elpablo.fscslutsky.ui.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elpablo.fscslutsky.core.utils.Response
import com.elpablo.fscslutsky.domain.model.Club
import com.elpablo.fscslutsky.domain.model.FullMatchInfo
import com.elpablo.fscslutsky.domain.model.Match
import com.elpablo.fscslutsky.domain.repository.MatchesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(private val repository: MatchesRepository) :
    ViewModel() {
    private val _viewState = MutableStateFlow(MatchesViewState())
    val viewState: StateFlow<MatchesViewState>
        get() = _viewState.asStateFlow()

    private val clubs = mutableListOf<Club>()
    private val allMatches = mutableListOf<Match>()
    private val allPreparedMatches = mutableListOf<FullMatchInfo>()

    init {
        getClubs()
    }

    private fun getClubs() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getClubs().collect { result ->
                when (result) {
                    Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is Response.Success -> {
                        if (result.data != null) {
                            clubs.addAll(result.data)
                        }
                        _viewState.update { state ->
                            state.copy(isLoading = false)
                        }
                        getPastMatches()
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

    private fun getPastMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPastMatches().collect { result ->
                when (result) {
                    Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is Response.Success -> {
                        if (result.data != null) {
                            allMatches.addAll(result.data)
                            _viewState.update { state ->
                                state.copy(index = result.data.size)
                            }
                        }
                        _viewState.update { state ->
                            state.copy(isLoading = false)
                        }
                        getUpcomingMatches()
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

    private fun getUpcomingMatches() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUpcomingMatches().collect { result ->
                when (result) {
                    Response.Loading -> {
                        _viewState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is Response.Success -> {
                        if (result.data != null) {
                            allMatches.addAll(result.data)

                        }
                        _viewState.update { state ->
                            state.copy(isLoading = false)
                        }
                        getAllMatches()
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

    private fun getAllMatches() {
        for (match in allMatches) {
            val homeClub = clubs.find { club -> club.id == match.homeId }
            val awayClub = clubs.find { club -> club.id == match.awayId }
            allPreparedMatches.add(
                FullMatchInfo(
                    homeClub = homeClub,
                    awayClub = awayClub,
                    title = match.title,
                    date = match.date,
                    homeScore = match.homeScore,
                    awayScore = match.awayScore,
                    location = match.location,
                    locationName = match.locationName
                )
            )
            _viewState.update { state ->
                state.copy(matches = allPreparedMatches)
            }
        }
    }
}