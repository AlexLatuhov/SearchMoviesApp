package com.example.searchmoviesapp.di.modules

import com.example.adsdkapi.InterstitialAdApiPreparer
import com.example.adsdkapi.NativeAdsRepository
import com.example.domain.ad.InterstitialAdUseCase
import com.example.domain.ad.LaunchCounter
import com.example.domain.movies.Repository
import com.example.domain.movies.SearchMoviesUseCase
import com.example.domain.movies.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {
    @Provides
    @ViewModelScoped
    fun provideSearchMoviesUseCase(
        repository: Repository,
        nativeAdsRepository: NativeAdsRepository
    ): SearchMoviesUseCase =
        SearchMoviesUseCase(
            repository,
            nativeAdsRepository
        )
    @Provides
    @ViewModelScoped
    fun provideToggleFavoritesUseCase(repository: Repository): ToggleFavoriteUseCase =
        ToggleFavoriteUseCase(repository)


    @Provides
    @ViewModelScoped
    fun provideInterstitialAdApiPreparer(
        interstitialAdApi: InterstitialAdApiPreparer,
        launchCounter: LaunchCounter
    ): InterstitialAdUseCase =
        InterstitialAdUseCase(interstitialAdApi, launchCounter)
}