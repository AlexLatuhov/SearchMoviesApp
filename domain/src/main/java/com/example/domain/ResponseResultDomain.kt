package com.example.domain

sealed class ResponseResultDomain {
    data class Success(val searchResult: List<DomainListItem>) : ResponseResultDomain()
    data class Error(val message: String?) : ResponseResultDomain()
}