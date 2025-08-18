package com.example.presentation.moviedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.presentation.MovieUiEntity
import com.example.presentation.R
import com.example.uicomponents.FavoriteButton
import com.example.uicomponents.PosterImage

@Composable
fun MovieDetailsItem(
    movie: MovieUiEntity, modifier: Modifier = Modifier,
    onToggle: () -> Unit
) {

    Card(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                PosterImage(url = movie.poster, description = movie.title)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(R.string.year_of_release, movie.year),
                        style = MaterialTheme.typography.labelMedium,
                    )

                    Text(
                        text = stringResource(R.string.type, movie.type),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                FavoriteButton(
                    isFavorite = movie.isFavorite,
                    onToggle = onToggle
                )
            }
        }
    }
}
