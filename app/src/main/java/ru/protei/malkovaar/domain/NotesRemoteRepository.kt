package ru.protei.malkovaar.domain

interface NotesRemoteRepository {
    suspend fun list(): List<Note>
    suspend fun add(note: Note): Long?
    suspend fun update(note: Note): Boolean
    suspend fun delete(note: Note): Boolean
}