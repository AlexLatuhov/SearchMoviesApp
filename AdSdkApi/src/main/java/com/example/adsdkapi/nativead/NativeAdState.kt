package com.example.adsdkapi.nativead

sealed class NativeAdState {
    class NativeAdsLoaded(val loadedAds: Pair<NativeAdEntity, NativeAdEntity>) : NativeAdState()
    object Failed : NativeAdState()
    object None : NativeAdState()
}