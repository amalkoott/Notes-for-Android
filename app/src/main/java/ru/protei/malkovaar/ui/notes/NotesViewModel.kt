package ru.protei.malkovaar.ui.notes

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.protei.malkovaar.domain.Note

class NotesViewModel : ViewModel() {
    val notes = mutableStateListOf<Note>(
        Note("Журнал","Надо посмотреть статьи на коридоры, большие пробелы, базовые линии и еще сделать спуск полос"),
        Note("Grimace","Пост в твиттер про памп sundae и grimace (с хештегом #Grimace100x!!!)"),
        Note("Sundae listing ","Did you hear about \$Sundae listing on  Bitmart yet? Come on to the exchange now!"),
        Note("Изоляция транзакций","есть уровни - то, насколько сильно влияют друг на друга параллельно выполняющиеся транзакции"),
        Note("Что приготовить","Лазанья, сырники, тбилиси, желе, фунчоза, картошка по-деревенски"),
    )
    var selected = mutableStateOf<Note?>(null )

    //  в выбранную заметку заносит новые строки
    fun onNoteChange(title: String, text: String){
            selected.value!!.title = title
            selected.value!!.text = text
    }

    // выбранную заметку сохраняет
    fun onEditComplete(){
            selected.value = null
    }

    // помечает замету как выбранную
    fun onNoteSelected(note: Note){
            selected.value = note
    }

    // создает новую пустую заметку и помечает ее выбранной
    fun onAddNoteClicked(){
            notes.add(selected.value!!)
            onEditComplete()
    }

}