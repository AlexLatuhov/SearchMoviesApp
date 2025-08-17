package com.example.presentation

sealed class UiState {
    object Loading : UiState()
    class Error(val message: String) : UiState()
    object DefaultError : UiState()
    object WelcomeMessage : UiState()
    data class Success(val movies: List<MovieUiEntity>, var openedMovie: MovieUiEntity? = null) :
        UiState()
}