package com.example.adsdkapi.nativead

import android.content.Context
import android.view.View

interface NativeAdApiViewFactory {
    @Throws(IllegalStateException::class)
    fun createView(context: Context, nativeAdEntity: NativeAdEntity): View
}