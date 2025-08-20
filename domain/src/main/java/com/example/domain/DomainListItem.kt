package com.example.domain

import com.example.adsdkapi.nativead.NativeAdEntity
import com.example.domain.movies.MovieDomainEntity

sealed class DomainListItem() {
    class MovieListItem(val movieDomainEntity: MovieDomainEntity) : DomainListItem()
    class NativeAdListItem(val nativeAdEntity: NativeAdEntity) : DomainListItem()
}

fun MovieDomainEntity.toDomainListItem() = DomainListItem.MovieListItem(this)