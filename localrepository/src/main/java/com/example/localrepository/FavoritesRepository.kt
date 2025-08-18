package com.example.localrepository

import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val dao: FavoriteMovieDao
) {
    suspend fun addFavorite(movieId: String) {
        val entity = FavoriteMovieEntity(imdbID = movieId)
        dao.insert(entity)
    }

    suspend fun removeFavorite(movieId: String) {
        dao.deleteById(movieId)
    }

    suspend fun isFavorite(movieId: String) = dao.isFavoriteOnce(id = movieId) > 0

    fun getExistingIds(ids: List<String>) = dao.getExistingIds(ids)
}
