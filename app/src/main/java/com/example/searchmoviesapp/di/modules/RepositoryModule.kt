package com.example.searchmoviesapp.di.modules

import com.example.data.RepositoryImpl
import com.example.domain.Repository
import com.example.domain.SearchMoviesUseCase
import com.example.remoterepository.OmdbRepositoryImpl
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
    fun provideRepository(omdbRepositoryImpl: OmdbRepositoryImpl): Repository =
        RepositoryImpl(omdbRepositoryImpl)

    @Provides
    @ViewModelScoped
    fun provideSearchMoviesUseCase(repository: Repository): SearchMoviesUseCase =
        SearchMoviesUseCase(repository)
}