package com.example.searchmoviesapp.di.modules

import com.example.data.RepositoryImpl
import com.example.domain.movies.Repository
import com.example.localrepository.FavoritesRepository
import com.example.remoterepository.OmdbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(
        omdbRepository: OmdbRepository,
        favoritesRepository: FavoritesRepository
    ): Repository =
        RepositoryImpl(omdbRepository, favoritesRepository)

}