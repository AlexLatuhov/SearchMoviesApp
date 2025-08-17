package com.example.presentation

import com.example.domain.MovieDomainEntity

data class MovieUiEntity(
    val title: String,
    val year: String,
    val imdbID: String,
    val type: String,
    val poster: String,
    val isFavorite: Boolean
)

fun MovieDomainEntity.toUi() = MovieUiEntity(
    title = title,
    year = year,
    imdbID = imdbID,
    type = type,
    poster = poster,
    isFavorite = isFavorite
)
