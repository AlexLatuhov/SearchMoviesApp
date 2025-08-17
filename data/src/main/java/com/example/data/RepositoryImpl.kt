package com.example.data

import com.example.domain.MovieDomainEntity
import com.example.domain.Repository
import com.example.domain.RequestResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {
    override suspend fun searchMovies(query: String): RequestResponse {//todo change to remote repository usage
        return RequestResponse.Success(
            listOf(
                MovieDomainEntity("title1", "year1", "ID1", "type1", "poster", false),
                MovieDomainEntity("title2", "year2", "ID2", "type2", "poster", false)
            ))
    }
}