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

/**
 * Searching movies and optionally inserting native ads into the result list.
 *
 * Algorithm:
 * 1) Delegates search to [Repository.searchMovies] (it's a Repository having remote and external ones, mixing their data together).
 * 2) On success:
 *    - If movie count > [ITEM_COUNTS_TO_TRIGGER_AD], requests two native ads and inserts them at [AD_INDEX1] and [AD_INDEX3]
 *      (if ads are actually loaded).
 *    - Otherwise, returns the list of movies.
 * 3) On any error — returns [ResponseResultDomain.Error].
 *
 * Reactive behavior:
 * - Uses flatMapLatest: a new search cancels the previous one.
 * - When ads load from [nativeAdsRepository], the resulting list may re-emit with ads inserted.
 *
 * @property repository Data source for movies.
 * @property nativeAdsRepository Provider of native ads to be inserted into lists.
 */
class SearchMoviesUseCase(
    private val repository: Repository, private val nativeAdsRepository: NativeAdsRepository
) {

    /**
     * @param query The search query, text input from user
     * @return Cold [Flow] emitting [ResponseResultDomain]:
     *   - Success(List<DomainListItem>) with optional [com.example.domain.DomainListItem.NativeAdListItem] insertions;
     *   - Error(message) on failure.
     */
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