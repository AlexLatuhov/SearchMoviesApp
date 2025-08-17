package com.example.remoterepository

import jakarta.inject.Inject

class OmdbRepositoryImpl @Inject constructor(
    private val api: OmdbApi
) {
    suspend fun searchMovies(query: String): OmdbResponse {
        val raw = api.search(query)
        return if (raw.response.equals("True", ignoreCase = true) && !raw.search.isNullOrEmpty()) {
            OmdbResponse.Success(raw.search)
        } else {
            OmdbResponse.Error(raw.error)
        }
    }
}
