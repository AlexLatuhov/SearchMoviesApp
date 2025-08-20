package com.example.presentation

import com.example.adsdkapi.nativead.NativeAdEntity
import com.example.domain.DomainListItem

sealed class SearchMovieUiListItem() {
    class MovieUiListItem(val movieUiEntity: MovieUiEntity) : SearchMovieUiListItem()
    class NativeAdUiListItem(val nativeAdEntity: NativeAdEntity) : SearchMovieUiListItem()
}

fun DomainListItem.toMovieUiListItem() =
    when (this) {
        is DomainListItem.MovieListItem -> SearchMovieUiListItem.MovieUiListItem(this.movieDomainEntity.toUi())
        is DomainListItem.NativeAdListItem -> SearchMovieUiListItem.NativeAdUiListItem(this.nativeAdEntity)
    }
