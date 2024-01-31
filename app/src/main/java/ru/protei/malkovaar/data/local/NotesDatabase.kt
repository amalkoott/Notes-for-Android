package ru.protei.malkovaar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.protei.malkovaar.domain.Note

@Database(
    entities = [Note::class],
    version = 3,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase(){
   abstract fun notesDao(): NotesDao

}