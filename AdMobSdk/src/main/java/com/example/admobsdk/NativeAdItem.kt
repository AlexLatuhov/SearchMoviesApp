package com.example.admobsdk

const val ITEMS_COUNT_TO_TRIGGER_AD = 3

data class NativeAdItem(//todo
    val position: Int,
    var nativeAdWrapper: NativeAdWrapper = NativeAdWrapper.None
)