package com.example.searchmoviesapp.di.modules

import android.content.Context
import com.example.admobsdk.NativeAdMobRepository
import com.example.admobsdk.NativeAdMobViewFactory
import com.example.adsdkapi.NativeAdApiViewFactory
import com.example.adsdkapi.NativeAdsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NativeAdPreparersBindsModule {
    @Provides
    @Singleton
    fun provideNativeAdsRepository(@ApplicationContext context: Context): NativeAdsRepository =
        NativeAdMobRepository(context)

    @Provides
    @Singleton
    fun provideNativeAdMobViewFactory(): NativeAdApiViewFactory =
        NativeAdMobViewFactory()
}