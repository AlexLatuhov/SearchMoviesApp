package com.example.presentation.ad

import com.example.domain.ad.InterstitialAdDomainState


sealed class InterstitialAdUiState {
    object None : InterstitialAdUiState()
    object Ready : InterstitialAdUiState()
}

fun InterstitialAdDomainState.toUi() =
    when (this) {
        InterstitialAdDomainState.Ready -> InterstitialAdUiState.Ready
        else -> InterstitialAdUiState.None
    }