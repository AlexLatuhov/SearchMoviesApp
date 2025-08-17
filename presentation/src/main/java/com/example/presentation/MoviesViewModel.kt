package com.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.RequestResponse
import com.example.domain.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesViewModel @Inject constructor(private val searchMoviesUseCase: SearchMoviesUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.WelcomeMessage)
    val uiState: StateFlow<UiState> = _uiState
    private var searchJob: Job? = null

    fun searchMovies(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = UiState.Loading
            when (val r = searchMoviesUseCase.searchMovies(searchText)) {
                is RequestResponse.Success -> _uiState.value =
                    UiState.Success(r.search.map { it.toUi() })

                is RequestResponse.Error -> {
                    val errorMessage = r.message
                    _uiState.value = if (errorMessage != null) {
                        UiState.Error(errorMessage)
                    } else {
                        UiState.DefaultError
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