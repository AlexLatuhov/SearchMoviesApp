package com.example.domain

import com.example.adsdkapi.nativead.NativeAdEntity
import com.example.adsdkapi.nativead.NativeAdState
import com.example.adsdkapi.nativead.NativeAdsRepository
import com.example.domain.movies.MovieDomainEntity
import com.example.domain.movies.Repository
import com.example.domain.movies.ResponseResult
import com.example.domain.movies.SearchMoviesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


class SearchMoviesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    //mocks
    val repository: Repository = mockk()
    val nativeAdsRepository: NativeAdsRepository = mockk()

    //test object
    val searchMoviesUseCase = SearchMoviesUseCase(repository, nativeAdsRepository)

    @Test
    fun `When search has no results`() = runTest {
        //given
        val expected = ResponseResultDomain.Error(null)
        val expectedRepositoryResult = ResponseResult.Error(null)
        every { repository.searchMovies(any()) } returns flowOf(expectedRepositoryResult)
        every { nativeAdsRepository.provideTwoAds() } returns flowOf(NativeAdState.Failed)
        //when
        val result: ResponseResultDomain? = searchMoviesUseCase.searchMovies("query").first()
        //then
        assertEquals(result, expected)
    }

    @Test
    fun `When search has only 2 movies`() = runTest {
        //given
        val expectedRepositoryResult =
            ResponseResult.Success(
                listOf(
                    MovieDomainEntity(
                        imdbID = "1",
                        title = "",
                        year = "",
                        type = "",
                        poster = "",
                        isFavorite = false
                    ),
                    MovieDomainEntity(
                        imdbID = "2",
                        title = "",
                        year = "",
                        type = "",
                        poster = "",
                        isFavorite = false
                    )
                )
            )
        every { repository.searchMovies(any()) } returns flowOf(expectedRepositoryResult)
        every { nativeAdsRepository.provideTwoAds() } returns flowOf(
            NativeAdState.NativeAdsLoaded(
                mockk()
            )
        )
        //when
        val result: ResponseResultDomain? = searchMoviesUseCase.searchMovies("query").first()
        //then
        assertIs<ResponseResultDomain.Success>(result)
        assertEquals(0, result.searchResult.count { it is DomainListItem.NativeAdListItem })
    }

    @Test
    fun `When search has 3 movies`() = runTest {
        //given
        val expectedRepositoryResult =
            ResponseResult.Success(
                listOf(
                    MovieDomainEntity(
                        imdbID = "1",
                        title = "",
                        year = "",
                        type = "",
                        poster = "",
                        isFavorite = false
                    ),
                    MovieDomainEntity(
                        imdbID = "2",
                        title = "",
                        year = "",
                        type = "",
                        poster = "",
                        isFavorite = false
                    ),
                    MovieDomainEntity(
                        imdbID = "3",
                        title = "",
                        year = "",
                        type = "",
                        poster = "",
                        isFavorite = false
                    ),
                )
            )
        every { repository.searchMovies(any()) } returns flowOf(expectedRepositoryResult)
        every { nativeAdsRepository.provideTwoAds() } returns flowOf(
            NativeAdState.NativeAdsLoaded(
                Pair(NativeAdEntity(mockk()), NativeAdEntity(mockk()))
            )
        )
        //when
        val result: ResponseResultDomain? = searchMoviesUseCase.searchMovies("query").first()
        //then
        assertIs<ResponseResultDomain.Success>(result)
        assertEquals(2, result.searchResult.count { it is DomainListItem.NativeAdListItem })
    }
}