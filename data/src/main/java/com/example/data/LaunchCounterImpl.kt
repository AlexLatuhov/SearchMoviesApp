package com.example.data


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.ad.LaunchCounter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore("app_prefs")

@Singleton
class LaunchCounterImpl @Inject constructor(private val context: Context) : LaunchCounter {

    private val key = intPreferencesKey("launch_count_test")

    override val launchCount: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[key] ?: 0
    }

    override suspend fun increment() {
        context.dataStore.edit { prefs ->
            val current = prefs[key] ?: 0
            prefs[key] = current + 1
        }
    }
}