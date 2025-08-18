package com.example.presentation.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.presentation.MovieUiEntity
import com.example.uicomponents.AppBarWithBack


@Composable
fun MovieDetails(
    movie: MovieUiEntity,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onToggle: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface),
    ) {
        AppBarWithBack(movie.title) {
            onBackPressed()
        }
        MovieDetailsItem(movie = movie, onToggle = onToggle)
        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
    }
}