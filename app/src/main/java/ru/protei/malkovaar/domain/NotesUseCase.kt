package ru.protei.malkovaar.domain

import kotlinx.coroutines.flow.Flow
import ru.protei.malkovaar.data.NotesRepositoryDB

class NotesUseCase(
    private val notesRepo: NotesRepositoryDB
) {
    suspend fun fillWithInitialNotes(initialNotes: List<Note>){
        // должен очистить содержимое базы
        notesRepo.clearDatabase()
        // затем добавить в базу переданный список заметок
        notesRepo.fillDatabase(initialNotes)
    }
    fun notesFlow(): Flow<List<Note>>{
        var flow : Flow<List<Note>>
        // метод подписки на данные (что бы это ни значило)
        return notesRepo.loadAllNotesFlow()
    }

    suspend fun save(note: Note){
        if (note.id == null){
            notesRepo.add(note)
        }else{
            notesRepo.update(note)
        }
    }
}