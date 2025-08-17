package com.example.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.theme.ContrastAwareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MoviesViewModel = hiltViewModel()

            ContrastAwareTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                Surface {
                    SearchMoviesScreens(
                        uiState = uiState,
                        searchMovies = { searchText ->
                            viewModel.searchMovies(searchText)
                        },
                        closeDetailScreen = {
                            viewModel.closeDetailScreen()
                        },
                        navigateToDetail = { emailId ->
                            viewModel.setOpenedMovie(emailId)
                        },
                    )
                }
            }
        }
    }
}