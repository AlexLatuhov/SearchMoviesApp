package com.example.localrepository

import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val dao: FavoriteMovieDao
) {

    suspend fun toggleFavorite(id: String) = dao.toggleFavorite(id)

    fun getExistingIds(ids: List<String>) = dao.getExistingIds(ids)
}
