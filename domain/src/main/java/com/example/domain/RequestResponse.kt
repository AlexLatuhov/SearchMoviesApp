package com.example.domain

sealed class RequestResponse {
    data class Success(val search: List<MovieDomainEntity>) : RequestResponse()
    data class Error(val message: String?) : RequestResponse()
}