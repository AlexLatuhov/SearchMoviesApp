package com.example.localrepository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies WHERE imdbID = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE imdbID = :id)")
    fun observeIsFavorite(id: String): Flow<Boolean>

    @Query("SELECT COUNT(*) FROM favorite_movies WHERE imdbID = :id")//todo change
    suspend fun isFavoriteOnce(id: String): Int


    @Query("SELECT imdbID FROM favorite_movies WHERE imdbID IN (:ids)")
    fun getExistingIds(ids: List<String>): Flow<List<String>>
}