package com.example.domain.movies

import com.example.adsdkapi.nativead.NativeAdState
import com.example.adsdkapi.nativead.NativeAdsRepository
import com.example.domain.DomainListItem
import com.example.domain.ResponseResultDomain
import com.example.domain.toDomainListItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


private const val ITEM_COUNTS_TO_TRIGGER_AD = 3
private const val AD_INDEX1 = 1
private const val AD_INDEX3 = 3

class SearchMoviesUseCase(
    private val repository: Repository, private val nativeAdsRepository: NativeAdsRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun searchMovies(query: String): Flow<ResponseResultDomain> {
        return repository.searchMovies(query).flatMapLatest { responseResult ->
            when (responseResult) {
                is ResponseResult.Success -> {
                    handleSuccessResult(responseResult.searchResult)
                }

                is ResponseResult.Error -> flowOf(ResponseResultDomain.Error(responseResult.message))
            }
        }.catch { emit(ResponseResultDomain.Error(it.message)) }
    }

    private fun handleSuccessResult(movies: List<MovieDomainEntity>): Flow<ResponseResultDomain> {
        return if (movies.size > ITEM_COUNTS_TO_TRIGGER_AD) {
            nativeAdsRepository.provideTwoAds().map { nativeAdState ->
                movies.map { it.toDomainListItem() as DomainListItem }
                    .toMutableList().apply {
                        when (nativeAdState) {
                            is NativeAdState.NativeAdsLoaded -> {
                                add(
                                    AD_INDEX1,
                                    DomainListItem.NativeAdListItem(nativeAdState.loadedAds.first)
                                )
                                add(
                                    AD_INDEX3,
                                    DomainListItem.NativeAdListItem(nativeAdState.loadedAds.second)
                                )
                            }

                            else -> {}
                        }
                    }.let {
                        ResponseResultDomain.Success(it)
                    }
            }
        } else {
            flowOf(ResponseResultDomain.Success(movies.map {
                it.toDomainListItem()
            }))
        }
    }
}