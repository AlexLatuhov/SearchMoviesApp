package com.example.data

import com.example.domain.movies.MovieDomainEntity
import com.example.remoterepository.Movie

data class MovieDataEntity(
    val title: String,
    val year: String,
    val imdbID: String,
    val type: String,
    val poster: String,
    val isFavorite: Boolean
)

fun MovieDataEntity.toDomain() = MovieDomainEntity(
    title = title,
    year = year,
    imdbID = imdbID,
    type = type,
    poster = poster,
    isFavorite = isFavorite
)

fun Movie.toData(isFavorite: Boolean) = MovieDataEntity(
    title = title,
    year = year,
    imdbID = imdbID,
    type = type,
    poster = poster,
    isFavorite = isFavorite
)