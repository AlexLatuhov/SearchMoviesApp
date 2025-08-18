package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.MovieDomainEntity
import com.example.domain.ResponseResult
import com.example.domain.SearchMoviesUseCase
import com.example.domain.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.WelcomeMessage)
    val uiState: StateFlow<UiState> = _uiState
    private var searchJob: Job? = null

    fun toggleFavorite(imdbID: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase.toggleFavorite(imdbID)
        }
    }

    private fun updateUiState(search: List<MovieDomainEntity>) {
        val newMoviesUi = search.map { it.toUi() }
        _uiState.getAndUpdate { state ->
            when (state) {
                is UiState.Success -> UiState.Success(
                    newMoviesUi,
                    openedMovie = newMoviesUi.find { state.openedMovie?.imdbID == it.imdbID }
                )

                else -> UiState.Success(newMoviesUi)
            }
        }
    }

    fun searchMovies(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = UiState.Loading

            searchMoviesUseCase.searchMovies(searchText).collect { result ->
                when (result) {
                    is ResponseResult.Success -> updateUiState(result.search)
                    is ResponseResult.Error -> {
                        val errorMessage = result.message
                        _uiState.value = if (errorMessage != null) {
                            UiState.Error(errorMessage)
                        } else {
                            UiState.DefaultError
                        }
                    }
                }
            }
        }
    }

    fun setOpenedMovie(imdbID: String) {
        _uiState.update { state ->
            when (state) {
                is UiState.Success -> {
                    val movie = state.movies.firstOrNull { it.imdbID == imdbID }
                    state.copy(openedMovie = movie)
                }

                else -> state
            }
        }
    }

    fun closeDetailScreen() {
        _uiState.update { state ->
            when (state) {
                is UiState.Success -> state.copy(openedMovie = null)
                else -> state
            }
        }
    }
}