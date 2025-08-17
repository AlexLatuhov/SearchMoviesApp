package com.example.domain

import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val repository: Repository) {
    suspend fun searchMovies(query: String): RequestResponse {
        val result = repository.searchMovies(query)
        return result
    }
}