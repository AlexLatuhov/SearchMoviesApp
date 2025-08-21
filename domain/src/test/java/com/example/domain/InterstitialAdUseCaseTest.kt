package com.example.domain

import com.example.adsdkapi.interstitial.InterstitialAdApiPreparer
import com.example.adsdkapi.interstitial.InterstitialAdState
import com.example.domain.ad.InterstitialAdDomainState
import com.example.domain.ad.InterstitialAdUseCase
import com.example.domain.ad.LaunchCounter
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

class InterstitialAdUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val launchCounter: LaunchCounter = mockk()
    val interstitialAdApi: InterstitialAdApiPreparer = mockk()

    //test object
    val interstitialAdUseCase = InterstitialAdUseCase(interstitialAdApi, launchCounter)

    @Test
    fun `When app started for the first time`() = runTest {
        //given
        every { launchCounter.launchCount } returns flowOf(1)
        //when
        val resultOfAdLoading = interstitialAdUseCase.load().first()
        //then
        assertIs<InterstitialAdDomainState.None>(resultOfAdLoading)
    }

    @Test
    fun `When app started for the second time`() = runTest {
        //given
        every { launchCounter.launchCount } returns flowOf(2)
        every { interstitialAdApi.loadInterstitialAd() } returns flowOf(InterstitialAdState.Ready)
        //when
        val resultOfAdLoading = interstitialAdUseCase.load().first()
        //then
        assertIs<InterstitialAdDomainState.Ready>(resultOfAdLoading)
    }

    @Test
    fun `When app started for the second time and load ad failed`() = runTest {
        //given
        every { launchCounter.launchCount } returns flowOf(2)
        every { interstitialAdApi.loadInterstitialAd() } returns flowOf(InterstitialAdState.None)
        //when
        val resultOfAdLoading = interstitialAdUseCase.load().first()
        //then
        assertIs<InterstitialAdDomainState.None>(resultOfAdLoading)
    }
}