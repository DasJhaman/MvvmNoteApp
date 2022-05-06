package com.task.noteapp.db

import androidx.room.*
import com.task.noteapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM NOTES WHERE noteTitle LIKE :query OR noteBody LIKE:query")
    fun searchNote(query: String?): Flow<List<Note>>

}