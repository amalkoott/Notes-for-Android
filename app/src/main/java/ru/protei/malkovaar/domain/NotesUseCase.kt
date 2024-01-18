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
        var saved: List<Note> = notesRepo.loadAllNotes()
        /*
        saved.forEach(){
            if(it.id == note.id){
                notesRepo.update(note)
            }else{
                notesRepo.add(note)
            }
        }
        */
        for (item in saved){
            if(item.id == note.id){
                notesRepo.update(note) // редактируем
                return
            }
        }
        notesRepo.add(note) // добавляем, т.к. заметка не нашлась в списке
    }
}