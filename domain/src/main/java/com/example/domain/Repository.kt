package com.example.domain

interface Repository {
    suspend fun searchMovies(query: String): RequestResponse
}