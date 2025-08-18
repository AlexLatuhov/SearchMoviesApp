package com.example.remoterepository

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OmdbRepository @Inject constructor(
    private val api: OmdbApi
) {
    fun searchMovies(query: String): Flow<OmdbResponse> = flow {
        val raw = api.search(query)
        if (raw.response.equals("True", ignoreCase = true) && !raw.search.isNullOrEmpty()) {
            emit(OmdbResponse.Success(raw.search))
        } else {
            emit(OmdbResponse.Error(raw.error))
        }
    }
}
