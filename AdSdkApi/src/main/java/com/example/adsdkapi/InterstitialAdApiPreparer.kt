package com.example.adsdkapi

import kotlinx.coroutines.flow.Flow

interface InterstitialAdApiPreparer {
    fun loadInterstitialAd(): Flow<InterstitialAdState>
}