package com.example.data

import com.example.domain.MovieDomainEntity
import com.example.domain.Repository
import com.example.domain.RequestResponse
import com.example.remoterepository.Movie
import com.example.remoterepository.OmdbRepositoryImpl
import com.example.remoterepository.OmdbResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val repositoryImpl: OmdbRepositoryImpl) :
    Repository {
    override suspend fun searchMovies(query: String): RequestResponse {
        val result = repositoryImpl.searchMovies(query)
        return result.toRequestResponse()
    }

    fun OmdbResponse.toRequestResponse() =
        when (this) {
            is OmdbResponse.Success -> RequestResponse.Success(this.search.map {
                it.toDomain()
            })

            is OmdbResponse.Error -> RequestResponse.Error(message = this.message)
        }

    fun Movie.toDomain() = MovieDomainEntity(
        title = title,
        year = year,
        imdbID = imdbID,
        type = type,
        poster = poster,
        isFavorite = false
    )
}

