package com.example.domain

import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(private val repository: Repository) {
    fun searchMovies(query: String) = repository.searchMovies(query)
}