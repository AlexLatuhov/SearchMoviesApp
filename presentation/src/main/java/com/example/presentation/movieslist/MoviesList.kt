package com.example.presentation.movieslist

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.presentation.MovieUiEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MoviesList(
    movies: List<MovieUiEntity>,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
        state = emailLazyListState,
    ) {
        itemsIndexed(items = movies) { index, movie ->
            MoviesListItem(
                movie = movie,
                navigateToDetail = { id ->
                    navigateToDetail(id)
                })
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}
