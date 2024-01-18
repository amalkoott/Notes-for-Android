package ru.protei.malkovaar

import NotesScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import ru.protei.malkovaar.data.NotesDatabase
import ru.protei.malkovaar.data.NotesRepositoryDB
import ru.protei.malkovaar.domain.NotesUseCase
import ru.protei.malkovaar.ui.notes.NotesViewModel
import ru.protei.malkovaar.ui.theme.MalkovaarTheme

class MainActivity : ComponentActivity() {
    private val database: NotesDatabase by lazy{
        Room.databaseBuilder(
            this,
            NotesDatabase::class.java,"notes_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    private val notesRepo by lazy { NotesRepositoryDB(database.notesDao())}
    private  val notesUseCase by lazy { NotesUseCase(notesRepo)}

    private val notesViewModel: NotesViewModel by viewModels {
        viewModelFactory {
            initializer {
                NotesViewModel(notesUseCase)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MalkovaarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ){
                    NotesScreen(notesViewModel)
                }
            }
        }
    }
}