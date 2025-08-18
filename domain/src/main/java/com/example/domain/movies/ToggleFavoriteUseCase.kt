package com.example.domain.movies

import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: Repository) {
    suspend fun toggleFavorite(itemId: String) = repository.toggleFavorite(itemId)
}