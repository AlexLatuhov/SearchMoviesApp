package com.example.searchmoviesapp.di.modules

import android.content.Context
import com.example.admobsdk.InterstitialAdMobManager
import com.example.adsdkapi.InterstitialAdApiDisplay
import com.example.adsdkapi.InterstitialAdApiPreparer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InterstitialAdPrepareModule {

    @Provides
    @Singleton
    fun provideManager(@ApplicationContext context: Context): InterstitialAdMobManager =
        InterstitialAdMobManager(context)

    @Provides
    @Singleton
    fun providePreparer(manager: InterstitialAdMobManager): InterstitialAdApiPreparer = manager

    @Provides
    @Singleton
    fun provideAdDisplay(manager: InterstitialAdMobManager): InterstitialAdApiDisplay = manager
}