package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DomainListItem
import com.example.domain.ResponseResultDomain
import com.example.domain.ad.InterstitialAdUseCase
import com.example.domain.movies.SearchMoviesUseCase
import com.example.domain.movies.ToggleFavoriteUseCase
import com.example.presentation.ad.InterstitialAdUiState
import com.example.presentation.ad.toUi
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
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val interstitialAdUseCase: InterstitialAdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.WelcomeMessage)
    val uiState: StateFlow<UiState> = _uiState

    private val _interstitialAdUiState = MutableStateFlow<InterstitialAdUiState>(
        InterstitialAdUiState.None
    )
    val interstitialAdUiState: StateFlow<InterstitialAdUiState> = _interstitialAdUiState
    private var searchJob: Job? = null

    fun toggleFavorite(imdbID: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase.toggleFavorite(imdbID)
        }
    }

    fun loadInterstitialAd() {
        viewModelScope.launch {
            interstitialAdUseCase.load().collect { result ->
                _interstitialAdUiState.value = result.toUi()
            }
        }
    }

    private fun updateUiState(search: List<DomainListItem>) {
        val newMoviesUi = search.map { it.toMovieUiListItem() }
        _uiState.getAndUpdate { state ->
            when (state) {
                is UiState.Success -> UiState.Success(
                    newMoviesUi,
                    openedMovie = newMoviesUi.filterIsInstance<SearchMovieUiListItem.MovieUiListItem>()
                        .firstOrNull {
                            it.movieUiEntity.imdbID == state.openedMovie?.imdbID
                        }?.movieUiEntity
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
                    is ResponseResultDomain.Success -> updateUiState(result.searchResult)
                    is ResponseResultDomain.Error -> {
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
                    val movie =
                        state.itemsList.filterIsInstance<SearchMovieUiListItem.MovieUiListItem>()
                            .firstOrNull {
                                it.movieUiEntity.imdbID == imdbID
                            }
                    state.copy(openedMovie = movie?.movieUiEntity)
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