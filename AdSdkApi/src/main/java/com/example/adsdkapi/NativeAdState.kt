package com.example.adsdkapi

sealed class NativeAdState {
    class NativeAdsLoaded(val loadedAds: Pair<NativeAdEntity, NativeAdEntity>) : NativeAdState()
    object Failed : NativeAdState()
}