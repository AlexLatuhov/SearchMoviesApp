package com.example.presentation.movieslist

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.presentation.R
import com.example.uicomponents.SimpleSearchField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesSearchBar(
    modifier: Modifier = Modifier,
    searchMovies: (String) -> Unit,
    enable: Boolean
) {
    var searchText by rememberSaveable { mutableStateOf("") }

    SimpleSearchField(
        value = searchText,
        onValueChange = { searchText = it },
        modifier = modifier,
        searchMovies = { searchMovies(searchText) },
        placeholder = stringResource(id = R.string.search_movies),
        enable = enable
    )
}