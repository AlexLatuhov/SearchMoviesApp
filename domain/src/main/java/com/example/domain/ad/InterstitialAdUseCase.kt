package com.example.domain.ad

import com.example.adsdkapi.InterstitialAdApiPreparer
import kotlinx.coroutines.flow.map

class InterstitialAdUseCase(
    private val interstitialAdApi: InterstitialAdApiPreparer
) {
    fun load() = interstitialAdApi.loadInterstitialAd().map { it.toDomain() }
}