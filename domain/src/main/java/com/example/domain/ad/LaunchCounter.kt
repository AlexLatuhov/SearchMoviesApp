package com.example.domain.ad

import kotlinx.coroutines.flow.Flow

interface LaunchCounter {
    val launchCount: Flow<Int>
    suspend fun increment()
}