package com.example.domain.movies

data class MovieDomainEntity(
    val title: String,
    val year: String,
    val imdbID: String,
    val type: String,
    val poster: String,
    val isFavorite: Boolean
)