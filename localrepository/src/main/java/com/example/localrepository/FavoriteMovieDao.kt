package com.example.localrepository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIgnore(favorite: FavoriteMovieEntity): Long

    @Query("DELETE FROM favorite_movies WHERE imdbID = :id")
    suspend fun deleteById(id: String): Int

    @Transaction
    suspend fun toggleFavorite(id: String) {
        val insertResult = insertIgnore(FavoriteMovieEntity(id))
        if (insertResult < 0) {
            deleteById(id)
        }
    }


    @Query("SELECT imdbID FROM favorite_movies WHERE imdbID IN (:ids)")
    fun getExistingIds(ids: List<String>): Flow<List<String>>
}