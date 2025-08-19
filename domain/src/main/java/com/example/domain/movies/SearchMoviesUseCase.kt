package com.example.domain.movies

import com.example.adsdkapi.NativeAdsRepository
import com.example.domain.DomainListItem
import com.example.domain.ResponseResultDomain
import com.example.domain.toDomainListItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private const val ITEM_COUNTS_TO_TRIGGER_AD = 3
private const val AD_INDEX1 = 1
private const val AD_INDEX3 = 3

class SearchMoviesUseCase @Inject constructor(
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
        }
    }

    private fun handleSuccessResult(movies: List<MovieDomainEntity>): Flow<ResponseResultDomain> {
        return if (movies.size > ITEM_COUNTS_TO_TRIGGER_AD) {
            nativeAdsRepository.provideTwoAds().map { pairAds ->
                movies.map { it.toDomainListItem() as DomainListItem }
                    .toMutableList().apply {
                        add(AD_INDEX1, DomainListItem.NativeAdListItem(pairAds.first))
                        add(AD_INDEX3, DomainListItem.NativeAdListItem(pairAds.second))
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