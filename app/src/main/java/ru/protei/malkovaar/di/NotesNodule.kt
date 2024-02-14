package ru.protei.malkovaar.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.protei.malkovaar.data.local.NotesDao
import ru.protei.malkovaar.data.local.NotesDatabase
import ru.protei.malkovaar.data.remote.NotesGitHubApi
import ru.protei.malkovaar.data.remote.NotesGitHubRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotesNodule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase{
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java, "notes_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(db: NotesDatabase): NotesDao{
        return db.notesDao()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
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
        return httpClient
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/repos/amalkoott/Notes-for-Android/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())

            .build()
    }

    @Provides
    @Singleton
    fun provideNotesGitHubApi(retrofit: Retrofit): NotesGitHubRepository{
        return NotesGitHubRepository(retrofit.create(NotesGitHubApi::class.java))
    }
}