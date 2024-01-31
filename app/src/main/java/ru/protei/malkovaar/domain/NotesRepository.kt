package ru.protei.malkovaar.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun loadAllNotes(): List<Note>
    fun loadAllNotesFlow(): Flow<List<Note>>
    suspend fun clearDatabase()
    suspend fun fillDatabase(notes: List<Note>)
    suspend fun add(note: Note)
    suspend fun update(note: Note)

    fun byRemoteID(remoteId: Long): Note?
    fun byEquals(title: String, text: String): Note?
}