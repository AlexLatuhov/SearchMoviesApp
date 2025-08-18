package com.example.searchmoviesapp.di.modules

import com.example.domain.Repository
import com.example.domain.SearchMoviesUseCase
import com.example.domain.ToggleFavoriteUseCase
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
    fun provideSearchMoviesUseCase(repository: Repository): SearchMoviesUseCase =
        SearchMoviesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun provideToggleFavoritesUseCase(repository: Repository): ToggleFavoriteUseCase =
        ToggleFavoriteUseCase(repository)
}