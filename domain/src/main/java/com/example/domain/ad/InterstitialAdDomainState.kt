package com.example.domain.ad

import com.example.adsdkapi.interstitial.InterstitialAdState

sealed class InterstitialAdDomainState {
    object None : InterstitialAdDomainState()
    object Ready : InterstitialAdDomainState()
}

fun InterstitialAdState.toDomain() =
    when (this) {
        InterstitialAdState.Ready -> InterstitialAdDomainState.Ready
        else -> InterstitialAdDomainState.None
    }