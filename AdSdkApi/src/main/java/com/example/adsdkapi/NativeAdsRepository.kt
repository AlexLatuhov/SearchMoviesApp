package com.example.adsdkapi

import kotlinx.coroutines.flow.Flow

interface NativeAdsRepository {
    //todo would be good to extend this solution to use List instead of Pair
    // in case we need to show more than two ads at the same time in the future
    fun provideTwoAds(): Flow<NativeAdState>
}