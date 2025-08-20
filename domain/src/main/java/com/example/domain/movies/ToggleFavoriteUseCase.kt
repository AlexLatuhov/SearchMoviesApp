package com.example.domain.movies

/**
 * Toggling the favorite status of a movie.
 *
 * Delegates to [Repository], encapsulating the business rule:
 * if the item is already a favorite — remove it; otherwise — add it.
 */
class ToggleFavoriteUseCase(private val repository: Repository) {
    suspend fun toggleFavorite(itemId: String) = repository.toggleFavorite(itemId)
}