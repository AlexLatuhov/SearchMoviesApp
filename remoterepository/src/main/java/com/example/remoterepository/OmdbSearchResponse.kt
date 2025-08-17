package com.example.remoterepository

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OmdbSearchResponse(
    @param:Json(name = "Search") val search: List<Movie>?,
    @param:Json(name = "Response") val response: String,
    @param:Json(name = "Error") val error: String?
)
