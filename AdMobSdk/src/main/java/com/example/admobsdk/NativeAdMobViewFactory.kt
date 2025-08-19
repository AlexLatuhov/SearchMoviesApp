package com.example.admobsdk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.adsdkapi.NativeAdApiViewFactory
import com.example.adsdkapi.NativeAdEntity
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

class NativeAdMobViewFactory : NativeAdApiViewFactory {

    override fun createView(
        context: Context,
        nativeAdEntity: NativeAdEntity
    ): View {
        val loadedNativeAd = nativeAdEntity.ad as? NativeAd ?: throw IllegalArgumentException()

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

        headline.text = loadedNativeAd.headline
        body.text = loadedNativeAd.body ?: ""
        cta.text = loadedNativeAd.callToAction ?: context.getString(R.string.learn_more)

        val iconDrawable = loadedNativeAd.icon?.drawable
        if (iconDrawable != null) {
            icon.setImageDrawable(iconDrawable)
            icon.visibility = View.VISIBLE
        } else {
            icon.visibility = View.GONE
        }

        view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                view.setNativeAd(loadedNativeAd)
            }

            override fun onViewDetachedFromWindow(v: View) {
                loadedNativeAd.destroy()
            }
        })

        return view
    }
}