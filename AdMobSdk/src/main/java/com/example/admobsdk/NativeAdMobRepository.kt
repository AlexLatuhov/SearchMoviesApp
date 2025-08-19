package com.example.admobsdk

import android.content.Context
import com.example.adsdkapi.NativeAdEntity
import com.example.adsdkapi.NativeAdState
import com.example.adsdkapi.NativeAdsRepository
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.LinkedBlockingQueue
import javax.inject.Inject

private const val NATIVE_AD_UNIT_ID = BuildConfig.ADMOB_NATIVE_AD
private const val MAX_ADS_COUNT = 5
private const val NEEDED_ADS_AMOUNT = 2
private const val PRELOAD_CYCLE_BREAK_IN_MS = 500L

class NativeAdMobRepository @Inject constructor(
    @param:ApplicationContext private val context: Context
) : NativeAdsRepository {

    var failed = 0
    val preparedAds = LinkedBlockingQueue<NativeAd>()
    val adLoader = AdLoader.Builder(context, NATIVE_AD_UNIT_ID).forNativeAd { nativeAd ->
        preparedAds.put(nativeAd)
    }.withAdListener(object : AdListener() {
        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
            failed++
        }
    }).withNativeAdOptions(NativeAdOptions.Builder().build()).build()

    fun preloadAds() {
        if (preparedAds.size < MAX_ADS_COUNT) {
            adLoader.loadAds(AdRequest.Builder().build(), MAX_ADS_COUNT)
        }
    }

    private fun failedToLoad() = failed >= MAX_ADS_COUNT

    //load more ads than needed for a single display in order to be able to show them as quickly as possible next time
    override fun provideTwoAds(): Flow<NativeAdState> =
        flow {
            failed = 0
            var isEmitted = false
            do {
                preloadAds()
                if (preparedAds.size >= NEEDED_ADS_AMOUNT) {
                    emit(
                        NativeAdState.NativeAdsLoaded(
                            Pair(
                                NativeAdEntity(preparedAds.take()),
                                NativeAdEntity(preparedAds.take())
                            )
                        )
                    )
                    isEmitted = true
                } else if (failedToLoad()) {
                    emit(NativeAdState.Failed)
                    isEmitted = true
                }
                delay(PRELOAD_CYCLE_BREAK_IN_MS)
            } while (!isEmitted)
        }
}
