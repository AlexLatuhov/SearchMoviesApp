package com.example.localrepository


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteMovieDaoInstrumentedTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: FavoriteMovieDao
    private lateinit var repository: FavoritesRepository

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.favoriteMovieDao()
        repository = FavoritesRepository(dao)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertToggle_inserts_when_not_existing() = runBlocking {
        val id = "tt001"

        // initially should not exist
        val before = dao.getExistingIds(listOf(id)).take(1).toList()
        assertTrue(before.isNotEmpty())
        assertTrue(before[0].isEmpty())

        // toggle -> should insert
        repository.toggleFavorite(id)

        val after = dao.getExistingIds(listOf(id)).take(1).toList()
        assertTrue(after.isNotEmpty())
        assertEquals(listOf(id), after[0])
    }

    @Test
    fun toggleTwice_removes_after_second_toggle() = runBlocking {
        val id = "tt002"

        // first toggle - insert
        repository.toggleFavorite(id)
        val afterInsert = dao.getExistingIds(listOf(id)).take(1).toList()
        assertEquals(listOf(id), afterInsert[0])

        // second toggle - should remove
        repository.toggleFavorite(id)
        val afterRemove = dao.getExistingIds(listOf(id)).take(1).toList()
        assertTrue(afterRemove[0].isEmpty())
    }

    @Test
    fun getExistingIds_returns_only_requested_ids() = runBlocking {
        val id1 = "a1"
        val id2 = "b2"
        val id3 = "c3"

        val askedIds = listOf(id1, id2, id3)

        // insert id1 and id2
        repository.toggleFavorite(id1)
        repository.toggleFavorite(id2)

        val existing = dao.getExistingIds(askedIds).take(1).toList()
        assertEquals(1, existing.size)
        val returned = existing[0].toSet()
        assertTrue(returned.contains(id1))
        assertTrue(returned.contains(id2))
        assertFalse(returned.contains(id3))
    }
}