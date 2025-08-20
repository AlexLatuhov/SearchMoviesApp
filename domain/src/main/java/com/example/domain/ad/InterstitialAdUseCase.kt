package com.example.domain.ad

import com.example.adsdkapi.interstitial.InterstitialAdApiPreparer
import com.example.adsdkapi.interstitial.InterstitialAdState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

private const val COUNTS_TO_TRIGGER_AD = 2

/**
 * Handling interstitial ad preload.
 *
 * Observes the app launch counter and triggers interstitial ad loading
 * through [InterstitialAdApiPreparer] once the threshold [COUNTS_TO_TRIGGER_AD] is reached.
 *
 * Behavior:
 * - Re-subscribes whenever the launch count changes (flatMapLatest).
 * - Emits [InterstitialAdState.Ready] when the threshold is reached and Ad is ready.
 * - Emits [InterstitialAdState.Ready] in any other case (error, loading).
 *
 * Concurrency/Threading - cold flow: work starts only upon collect().
 *
 * @property interstitialAdApi API for loading interstitial ads.
 * @property launchCounter Source of app launch count.
 */
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