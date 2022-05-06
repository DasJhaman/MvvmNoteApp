package com.task.noteapp.repository


import com.task.noteapp.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun updateNote(note: Note)
    fun getAllNotes(): Flow<List<Note>>
    fun searchNote(query: String?): Flow<List<Note>>
}
