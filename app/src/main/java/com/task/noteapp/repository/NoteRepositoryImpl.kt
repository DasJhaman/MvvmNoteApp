package com.task.noteapp.repository


import com.task.noteapp.db.NoteDao
import com.task.noteapp.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val dao: NoteDao) : NoteRepository {
    override suspend fun insertNote(note: Note) = dao.insertNote(note)
    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)
    override suspend fun updateNote(note: Note) = dao.updateNote(note)
    override fun getAllNotes(): Flow<List<Note>> = dao.getAllNotes()
    override fun searchNote(query: String?): Flow<List<Note>> = dao.searchNote(query)
}
