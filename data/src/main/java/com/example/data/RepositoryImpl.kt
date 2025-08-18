package com.example.data

import com.example.domain.Repository
import com.example.domain.ResponseResult
import com.example.localrepository.FavoritesRepository
import com.example.remoterepository.OmdbRepository
import com.example.remoterepository.OmdbResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val repositoryImpl: OmdbRepository,
    private val favoritesRepository: FavoritesRepository
) : Repository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchMovies(query: String): Flow<ResponseResult> {

        return repositoryImpl.searchMovies(query).flatMapLatest { omdb ->
            when (omdb) {
                is OmdbResponse.Success -> {
                    val searchList = omdb.search
                    val ids = searchList.map { it.imdbID }
                    favoritesRepository.getExistingIds(ids)
                        .map { favList ->
                            val favSet = favList.toHashSet()
                            val movies = searchList.map {
                                it.toData(favSet.contains(it.imdbID)).toDomain()
                            }
                            ResponseResult.Success(movies)
                        }
                }

                is OmdbResponse.Error -> {
                    flowOf(ResponseResult.Error(omdb.message))
                }
            }
        }.catch { emit(ResponseResult.Error(it.message)) }
    }

    override suspend fun toggleFavorite(itemId: String) {
        if (favoritesRepository.isFavorite(itemId)) {
            favoritesRepository.removeFavorite(itemId)
        } else {
            favoritesRepository.addFavorite(itemId)
        }
    }
}

