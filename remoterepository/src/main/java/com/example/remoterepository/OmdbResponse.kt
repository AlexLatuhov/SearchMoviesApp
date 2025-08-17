package com.example.remoterepository

sealed class OmdbResponse {
    data class Success(val search: List<Movie>) : OmdbResponse()
    data class Error(val message: String?) : OmdbResponse()
}