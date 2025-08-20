package com.example.adsdkapi.interstitial

import kotlinx.coroutines.flow.Flow

interface InterstitialAdApiPreparer {
    fun loadInterstitialAd(): Flow<InterstitialAdState>
}