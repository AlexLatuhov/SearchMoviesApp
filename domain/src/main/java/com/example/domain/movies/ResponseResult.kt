package com.example.domain.movies

sealed class ResponseResult {
    data class Success(val search: List<MovieDomainEntity>) : ResponseResult()
    data class Error(val message: String?) : ResponseResult()
}