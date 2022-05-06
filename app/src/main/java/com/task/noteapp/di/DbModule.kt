package com.task.noteapp.di

import android.content.Context
import androidx.room.Room
import com.task.noteapp.repository.NoteRepositoryImpl
import com.task.noteapp.db.NoteDatabase
import com.task.noteapp.db.NoteDao
import com.task.noteapp.helper.Constants
import com.task.noteapp.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        Constants.DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: NoteDatabase) =
        db.getNoteDao()

    @Singleton
    @Provides
    fun provideNoteRepository(dao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(dao)
    }

}