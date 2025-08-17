package com.example.remoterepository

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @param:Json(name = "Title") val title: String,
    @param:Json(name = "Year") val year: String,
    @param:Json(name = "imdbID") val imdbID: String,
    @param:Json(name = "Type") val type: String,
    @param:Json(name = "Poster") val poster: String
)
