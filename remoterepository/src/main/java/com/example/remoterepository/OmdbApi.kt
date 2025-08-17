package com.example.remoterepository

import jakarta.inject.Singleton
import retrofit2.http.GET
import retrofit2.http.Query

@Singleton
interface OmdbApi {
    @GET("/")
    suspend fun search(
        @Query("s") query: String,
        @Query("type") type: String? = null
    ): OmdbSearchResponse
}