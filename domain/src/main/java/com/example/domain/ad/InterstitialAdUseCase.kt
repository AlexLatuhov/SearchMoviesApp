package com.example.domain.ad

import com.example.adsdkapi.InterstitialAdApiPreparer
import com.example.adsdkapi.InterstitialAdState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

private const val COUNTS_TO_TRIGGER_AD = 2

class InterstitialAdUseCase(
    private val interstitialAdApi: InterstitialAdApiPreparer,
    private val launchCounter: LaunchCounter
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun load(): Flow<InterstitialAdDomainState> {
        return launchCounter.launchCount.flatMapLatest {
            if (it == COUNTS_TO_TRIGGER_AD) {
                interstitialAdApi.loadInterstitialAd()
            } else {
                flowOf(InterstitialAdState.None)
            }
        }.map { it.toDomain() }
    }
}