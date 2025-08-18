package com.example.admobsdk

import android.app.Activity
import android.content.Context
import com.example.adsdkapi.InterstitialAdApiDisplay
import com.example.adsdkapi.InterstitialAdApiPreparer
import com.example.adsdkapi.InterstitialAdState
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

const val INTERSTITIAL_AD_UNIT_ID = BuildConfig.ADMOB_INTERSTITIAL_ID
const val NATIVE_AD_UNIT_ID = BuildConfig.ADMOB_NATIVE_AD//todo use it

class InterstitialAdMobManager @Inject constructor(
    private val context: Context
) : InterstitialAdApiDisplay, InterstitialAdApiPreparer {
    private var interstitial: InterstitialAd? = null

    override fun showInterstitialAd(activity: Activity) {
        interstitial?.show(activity)
    }

    override fun loadInterstitialAd(): Flow<InterstitialAdState> = callbackFlow {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            INTERSTITIAL_AD_UNIT_ID,
            request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitial = ad
                    trySend(InterstitialAdState.Ready)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitial = null
                    trySend(InterstitialAdState.None)
                }
            }
        )
        awaitClose {
            interstitial = null
        }
    }
}
