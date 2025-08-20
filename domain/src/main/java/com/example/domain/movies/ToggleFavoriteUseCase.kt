package com.example.domain.movies

class ToggleFavoriteUseCase(private val repository: Repository) {
    suspend fun toggleFavorite(itemId: String) = repository.toggleFavorite(itemId)
}