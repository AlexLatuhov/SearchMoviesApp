package com.example.searchmoviesapp.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.domain.ad.LaunchCounterUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltAndroidApp
class SearchMoviesApp : Application() {

    @Inject
    lateinit var launchCounterUseCase: LaunchCounterUseCase

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(AppLifecycleCallbacks(launchCounterUseCase))
    }

    private class AppLifecycleCallbacks(private val launchCounterUseCase: LaunchCounterUseCase) :
        ActivityLifecycleCallbacks {

        private var startedActivityCount = 0
        private var launchCounted = false

        override fun onActivityStarted(activity: Activity) {
            if (startedActivityCount == 0 && !launchCounted) {
                ProcessLifecycleOwner.get().lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        launchCounterUseCase.increment()
                    }
                }
                launchCounted = true
            }
            startedActivityCount++
        }

        override fun onActivityStopped(activity: Activity) {
            startedActivityCount--
        }

        override fun onActivityResumed(activity: Activity) {}
        override fun onActivitySaveInstanceState(
            activity: Activity,
            outState: Bundle
        ) {
        }

        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityCreated(
            activity: Activity,
            savedInstanceState: Bundle?
        ) {
        }

        override fun onActivityDestroyed(activity: Activity) {}
    }
}