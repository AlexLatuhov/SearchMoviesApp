package com.example.domain

data class MovieDomainEntity(
    val title: String,
    val year: String,
    val imdbID: String,
    val type: String,
    val poster: String,
    val isFavorite: Boolean
)