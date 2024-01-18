package ru.protei.malkovaar.ui.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.protei.malkovaar.domain.Note
import ru.protei.malkovaar.domain.NotesUseCase

class NotesViewModel(
    private val notesUseCase: NotesUseCase
) : ViewModel() {

    val initialList = mutableStateListOf<Note>(
        Note("Журнал","Надо посмотреть статьи на коридоры, большие пробелы, базовые линии и еще сделать спуск полос"),
        Note("Grimace","Пост в твиттер про памп sundae и grimace (с хештегом #Grimace100x!!!)"),
        Note("Что приготовить","Лазанья, сырники, тбилиси, желе, фунчоза, картошка по-деревенски"),
    )

    val notes = MutableStateFlow<List<Note>>(initialList)

    init {
        viewModelScope.launch {
           // notesUseCase.fillWithInitialNotes(notes.value)

            notesUseCase.notesFlow()
                .collect{
                    note ->
                    notes.value = note
                }
        }
    }

    var selected = mutableStateOf<Note?>(null )

    //  в выбранную заметку заносит новые значения
    fun onNoteChange(title: String, text: String){
            selected.value!!.title = title
            selected.value!!.text = text
    }
    fun saveNote(note: Note){
        viewModelScope.launch {
            notesUseCase.save(selected.value!!)
        }

        onEditComplete()
    }
    // помечает, что редактирование закончено -> нет выбранных заметок
    fun onEditComplete(){
        selected.value = null
    }



}