package com.example.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.adsdkapi.InterstitialAdApiDisplay
import com.example.adsdkapi.NativeAdApiViewFactory
import com.example.presentation.ad.InterstitialAdUiState
import com.example.presentation.theme.ContrastAwareTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var interstitialAdApiDisplay: InterstitialAdApiDisplay

    @Inject
    lateinit var nativeAdApiViewFactory: NativeAdApiViewFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MoviesViewModel = hiltViewModel()
            viewModel.loadInterstitialAd()

            ContrastAwareTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val interstitialAdState by viewModel.interstitialAdUiState.collectAsStateWithLifecycle()
                if (interstitialAdState is InterstitialAdUiState.Ready) {
                    interstitialAdApiDisplay.showInterstitialAd(this)
                }

                Surface {
                    SearchMoviesScreens(
                        uiState = uiState,
                        searchMovies = { searchText ->
                            viewModel.searchMovies(searchText)
                        },
                        closeDetailScreen = {
                            viewModel.closeDetailScreen()
                        },
                        navigateToDetail = { movieId ->
                            viewModel.setOpenedMovie(movieId)
                        },
                        onToggle = { movieId ->
                            viewModel.toggleFavorite(movieId)
                        },
                        nativeAdApiViewFactory = nativeAdApiViewFactory
                    )
                }
            }
        }
    }
}