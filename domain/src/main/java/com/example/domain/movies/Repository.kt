package com.example.domain.movies

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun searchMovies(query: String): Flow<ResponseResult>
    suspend fun toggleFavorite(itemId: String)
}