package ru.protei.malkovaar.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.protei.malkovaar.domain.Note

@Dao
interface NotesDao {
    @Query("SELECT * FROM Note")
    fun all():List<Note>

    @Query("SELECT * FROM Note ORDER BY id ASC")
    fun allFlow(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Query("DELETE FROM Note WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM Note")
    fun deleteAll()

    @Query("SELECT * FROM Note WHERE remoteId = :remoteId")
    fun byRemoteID(remoteId: Long): Note

    @Query("SELECT * FROM Note WHERE title = :title AND text = :text")
    fun byEquals(title: String, text: String): Note
}