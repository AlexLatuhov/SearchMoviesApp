package com.example.presentation.moviedetails

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.example.presentation.MovieUiEntity
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MovieDetailsScreen(
    openedMovie: MovieUiEntity?,
    onClose: () -> Unit
) {
    var visible by remember { mutableStateOf(openedMovie != null) }
    LaunchedEffect(openedMovie) {
        visible = openedMovie != null
    }

    val state = remember { MutableTransitionState(visible) }
    LaunchedEffect(visible) { state.targetState = visible }

    LaunchedEffect(state) {
        snapshotFlow { state.isIdle && !state.targetState }
            .distinctUntilChanged()
            .collect { finished ->
                if (finished) {
                    onClose.invoke()
                }
            }
    }

    if (openedMovie != null) {
        BackHandler {
            visible = false
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { it / 2 }),
    ) {
        openedMovie?.let { movie ->
            MovieDetails(movie = movie) {
                visible = false
            }
        }
    }
}