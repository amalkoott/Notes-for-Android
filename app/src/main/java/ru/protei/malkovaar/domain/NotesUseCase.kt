package ru.protei.malkovaar.domain

import android.annotation.SuppressLint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.protei.malkovaar.data.remote.NotesGitHubRepository
import javax.inject.Inject

class NotesUseCase @Inject constructor(
    private val notesRepo: NotesRepository, //NotesRepository
    private val notesRemoteRepo: NotesGitHubRepository
) {
    suspend fun fillWithInitialNotes(initialNotes: List<Note>){
        // должен очистить содержимое базы
        notesRepo.clearDatabase()
        // затем добавить в базу переданный список заметок
        notesRepo.fillDatabase(initialNotes)
    }
    suspend fun loadRemoteNotes(){
        // получаем список всех заметок с сервера
        val listNotes: List<Note> = notesRemoteRepo.list()

        listNotes.forEach {
            updateLocalDB(it)
        }
    }
    fun notesFlow(): Flow<List<Note>>{
        var flow : Flow<List<Note>>
        // метод подписки на данные (что бы это ни значило)
        return notesRepo.loadAllNotesFlow()
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun updateLocalDB(note: Note) = withContext(Dispatchers.IO){
        val localList: List<Note> = notesRepo.loadAllNotes()
        if (localList.isEmpty()){ // если локальная БД пустая - полностью заполняем ее с сервера
            notesRepo.add(note)
            return@withContext
        }else{
            val localNote = notesRepo.byRemoteID(note.remoteId!!)
                if(localNote == null){ // если заметки с сервера нет в БД - добавляем
                    notesRepo.add(note)
                    return@withContext
                }else{
                    note.id = localNote.id // апдейтим имеющиеся в БД заметки (изменения были на сервере)
                    notesRepo.update(note)
                    return@withContext
                }
        }
    }
    suspend fun save(note: Note)= withContext(Dispatchers.IO){
        if (note.id == null){
            notesRepo.add(note)
        }else{
            notesRepo.update(note)
        }

        if(note.id == null){
            // добавляем новую заметку на сервер (и обновляем в локалке remoteID)
            var remoteID = notesRemoteRepo.add(note)
            var addedNote = notesRepo.byEquals(note.title, note.text)
            if (addedNote != null) {
                addedNote.remoteId = remoteID
                notesRepo.update(addedNote)
            }else return@withContext
        }else{
            // меняем сущесвующую на сервере заметку
            notesRemoteRepo.update(note)
        }
    }
}