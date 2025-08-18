package com.example.localrepository.di

import android.content.Context
import androidx.room.Room
import com.example.localrepository.AppDatabase
import com.example.localrepository.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
            .build()

    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavoriteMovieDao = db.favoriteMovieDao()
}