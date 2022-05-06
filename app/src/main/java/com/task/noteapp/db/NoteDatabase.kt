package com.task.noteapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.noteapp.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

}