package com.example.domain.ad

/**
 * Incrementing the app launch counter.
 * Threading - suspend; executes in the caller's coroutine context.
 * @property launchCounter Launch counter storage/service.
 */
class LaunchCounterUseCase(private val launchCounter: LaunchCounter) {
    suspend fun increment() {
        launchCounter.increment()
    }
}