package com.example.domain

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun searchMovies(query: String): Flow<ResponseResult>
    suspend fun toggleFavorite(itemId: String)
}