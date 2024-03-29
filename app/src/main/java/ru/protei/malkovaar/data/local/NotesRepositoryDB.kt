package ru.protei.malkovaar.data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.protei.malkovaar.domain.Note
import ru.protei.malkovaar.domain.NotesRepository
import javax.inject.Inject

class NotesRepositoryDB @Inject constructor(
    private val dao: NotesDao
) : NotesRepository {
    override fun loadAllNotesFlow(): Flow<List<Note>> = dao.allFlow()
    override suspend fun loadAllNotes(): List<Note> = withContext(Dispatchers.IO) {
        return@withContext dao.all()
    }
    override suspend fun clearDatabase() = withContext(Dispatchers.IO){
        dao.deleteAll()
    }
    override suspend fun fillDatabase(notes: List<Note>) {
        notes.forEach{
            dao.insert(it)
        }
    }
    override suspend fun add(note: Note): Unit = withContext(Dispatchers.IO) {
        dao.insert(note)
    }
    override suspend fun update(note: Note) = withContext(Dispatchers.IO){
        dao.update(note)
    }

    override fun byRemoteID(remoteId: Long): Note? {
        return dao.byRemoteID(remoteId)
    }
    override fun byEquals(title: String, text: String): Note? {
        return dao.byEquals(title, text)
    }
}