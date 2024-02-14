package ru.protei.malkovaar.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.protei.malkovaar.data.local.NotesRepositoryDB
import ru.protei.malkovaar.data.remote.NotesGitHubRepository
import ru.protei.malkovaar.domain.NotesRemoteRepository
import ru.protei.malkovaar.domain.NotesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {

    @Singleton
    @Binds
    fun bindNotesRepository(notesRepository: NotesRepositoryDB): NotesRepository

    @Singleton
    @Binds
    fun bindNotesRemoteRepository(notesRepo: NotesGitHubRepository): NotesRemoteRepository
}