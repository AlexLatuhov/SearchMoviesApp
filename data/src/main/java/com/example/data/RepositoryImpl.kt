package com.example.data

import com.example.domain.MovieDomainEntity
import com.example.domain.Repository
import com.example.domain.RequestResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {
    override suspend fun searchMovies(query: String): RequestResponse {//todo change to remote repository usage
        return RequestResponse.Success(
            listOf(
                MovieDomainEntity(
                    "title1",
                    "year1",
                    "ID1",
                    "type1",
                    "https://m.media-amazon.com/images/M/MV5BZjRiMDhiZjQtNjk5Yi00ZDcwLTkyYTEtMDc1NjdmNjFhNGIzXkEyXkFqcGc@._V1_SX300.jpg",
                    false
                ),
                MovieDomainEntity(
                    "title2",
                    "year2",
                    "ID2",
                    "type2",
                    "https://m.media-amazon.com/images/M/MV5BYmM3MTllNzYtN2MzNS00NWQwLTk0NTEtNjY1MmMwYjNkNTE5XkEyXkFqcGc@._V1_SX300.jpg",
                    false
                )
            ))
    }
}