package com.example.adsdkapi

sealed class InterstitialAdState {
    object None : InterstitialAdState()
    object Ready : InterstitialAdState()
}