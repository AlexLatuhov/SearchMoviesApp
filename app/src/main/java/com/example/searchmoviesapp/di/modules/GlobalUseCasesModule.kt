package com.example.searchmoviesapp.di.modules

import android.content.Context
import com.example.data.LaunchCounterImpl
import com.example.domain.ad.LaunchCounter
import com.example.domain.ad.LaunchCounterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GlobalUseCasesModule {

    @Provides
    @Singleton
    fun provideLaunchCounterUseCase(launchCounter: LaunchCounter): LaunchCounterUseCase =
        LaunchCounterUseCase(launchCounter)

    @Provides
    @Singleton
    fun provideLaunchCounter(@ApplicationContext context: Context): LaunchCounter =
        LaunchCounterImpl(context)
}