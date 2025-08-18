package com.example.presentation.movieslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.presentation.MovieUiEntity
import com.example.uicomponents.FavoriteButton


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesListItem(
    movie: MovieUiEntity,
    navigateToDetail: () -> Unit,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit
) {

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(CardDefaults.shape)
            .clickable(
                onClick = { navigateToDetail() }
            )
            .clip(CardDefaults.shape),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    text = movie.title + " " + movie.year,
                    style = MaterialTheme.typography.labelMedium,
                )
                FavoriteButton(
                    isFavorite = movie.isFavorite,
                    onToggle = onToggle
                )
            }
        }
    }
}