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
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.protei.malkovaar.data.local.NotesDatabase
import ru.protei.malkovaar.data.local.NotesRepositoryDB
import ru.protei.malkovaar.data.remote.NotesGitHubApi
import ru.protei.malkovaar.data.remote.NotesGitHubRepository
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

    var httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader(
                    "Authorization",
                    "Bearer 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"
                )
                .build()
            chain.proceed(request)
        }
        .build()

    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/repos/amalkoott/Notes-for-Android/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())

        .build()

    private val notesApi by lazy { NotesGitHubRepository(retrofit.create(NotesGitHubApi::class.java)) }
    private val notesRepo by lazy { NotesRepositoryDB(database.notesDao()) }
    private  val notesUseCase by lazy { NotesUseCase(notesRepo, notesApi)}

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