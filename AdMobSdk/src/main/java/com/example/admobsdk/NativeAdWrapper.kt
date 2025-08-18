package com.example.admobsdk

import com.google.android.gms.ads.nativead.NativeAd

sealed class NativeAdWrapper {
    //todo
    data class AdMob(val nativeAd: NativeAd) : NativeAdWrapper()

    object None : NativeAdWrapper()
}