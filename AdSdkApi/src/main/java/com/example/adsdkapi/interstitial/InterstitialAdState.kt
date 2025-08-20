package com.example.adsdkapi.interstitial

sealed class InterstitialAdState {
    object None : InterstitialAdState()
    object Ready : InterstitialAdState()
}