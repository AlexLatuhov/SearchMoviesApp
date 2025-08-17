package com.example.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.presentation.movieslist.MoviesList
import com.example.presentation.movieslist.MoviesSearchBar
import com.example.uicomponents.Message

@Composable
fun SearchMoviesScreens(
    uiState: UiState,
    modifier: Modifier = Modifier,
    searchMovies: (String) -> Unit
) {

    val moviesLazyListState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()) {
        MoviesAppContent(
            uiState = uiState,
            moviesLazyListState = moviesLazyListState,
            modifier = Modifier.fillMaxSize(),
            searchMovies = searchMovies
        )
    }
}

@Composable
fun MoviesAppContent(
    uiState: UiState,
    moviesLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    searchMovies: (String) -> Unit
) {
    Box(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)) {

        MoviesSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            searchMovies = searchMovies, enable = uiState !is UiState.Loading
        )

        when (uiState) {
            is UiState.Success -> {
                MoviesList(
                    movies = uiState.movies,
                    emailLazyListState = moviesLazyListState,
                    modifier = modifier
                )
            }

            is UiState.WelcomeMessage -> {
                Message(stringResource(R.string.welcome_message))
            }

            is UiState.Error -> {
                Message(uiState.message)
            }

            is UiState.DefaultError -> {
                Message(stringResource(R.string.error_during_request))
            }

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }

}