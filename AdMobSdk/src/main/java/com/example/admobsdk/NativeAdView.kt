package com.example.admobsdk

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

@Composable
fun NativeAdCard(//todo
    ad: NativeAdWrapper,
    modifier: Modifier = Modifier
) {
    when (ad) {
        is NativeAdWrapper.AdMob -> AdMobNativeAdCard(ad.nativeAd, modifier)
        else -> {}
    }
}

@Composable
private fun AdMobNativeAdCard(
    nativeAd: NativeAd,
    modifier: Modifier = Modifier
) {
    DisposableEffect(nativeAd) {
        onDispose { nativeAd.destroy() }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val view = LayoutInflater.from(context)
                .inflate(
                    R.layout.ad_native_small,
                    FrameLayout(context),
                    false
                ) as NativeAdView

            val mediaView =
                view.findViewById<MediaView>(R.id.ad_media)
            val headline = view.findViewById<TextView>(R.id.ad_headline)
            val body = view.findViewById<TextView>(R.id.ad_body)
            val cta = view.findViewById<Button>(R.id.ad_cta)
            val icon = view.findViewById<ImageView>(R.id.ad_icon)

            view.mediaView = mediaView
            view.headlineView = headline
            view.bodyView = body
            view.callToActionView = cta
            view.iconView = icon

            headline.text = nativeAd.headline
            body.text = nativeAd.body ?: ""
            cta.text = nativeAd.callToAction ?: context.getString(R.string.learn_more)

            val iconDrawable = nativeAd.icon?.drawable
            if (iconDrawable != null) {
                icon.setImageDrawable(iconDrawable)
                icon.visibility = View.VISIBLE
            } else {
                icon.visibility = View.GONE
            }

            view.setNativeAd(nativeAd)
            view
        }
    )
}
