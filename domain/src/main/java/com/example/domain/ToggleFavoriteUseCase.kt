package com.example.domain

import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: Repository) {
    suspend fun toggleFavorite(itemId: String) = repository.toggleFavorite(itemId)
}