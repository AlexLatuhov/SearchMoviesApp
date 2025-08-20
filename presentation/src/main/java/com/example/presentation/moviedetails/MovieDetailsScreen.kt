package com.example.presentation.moviedetails

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.presentation.MovieUiEntity

@Composable
fun MovieDetailsScreen(
    openedMovie: MovieUiEntity?,
    onClose: () -> Unit,
    onToggle: (String) -> Unit,
) {
    if (openedMovie == null) {
        return
    }

    Dialog(
        onDismissRequest = {
            onClose()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            MovieDetails(
                movie = openedMovie,
                onBackPressed = { onClose() },
                onToggle = { onToggle(openedMovie.imdbID) }
            )
        }
    }
}