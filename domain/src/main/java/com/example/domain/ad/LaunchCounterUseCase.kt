package com.example.domain.ad

class LaunchCounterUseCase(private val launchCounter: LaunchCounter) {
    suspend fun increment() {
        launchCounter.increment()
    }
}