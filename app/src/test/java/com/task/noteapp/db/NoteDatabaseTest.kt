package com.task.noteapp.db


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.task.noteapp.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = db.getNoteDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveTask_taskProvided_TaskSaved() = runBlocking {
        val aNote = Note(1, "test", "test")
        dao.insertNote(aNote)
        val notes = dao.getAllNotes().first()
        Truth.assertThat(notes).contains(aNote)
        Truth.assertThat(notes).hasSize(1)
    }

    @Test
    fun saveTask_sameTaskSavedAgain_ReplacesNewTaskWithPrevious() = runBlocking {
        val aNote = Note(1, "test", "test")
        val bNote = Note(1, "test", "test")
        var notes: List<Note>
        dao.insertNote(aNote)
        dao.insertNote(bNote)
        val job = launch {
            dao.getAllNotes().test {
                notes = awaitItem()
                Truth.assertThat(notes).contains(bNote)
                Truth.assertThat(notes).hasSize(1)
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }


    @Test
    fun saveTask_taskProvided_DeleteSavedTask() = runBlocking {
        val aNote = Note(1, "Jhaman test", "delete test")
        dao.insertNote(aNote)
        dao.deleteNote(aNote)
        val tasks = dao.getAllNotes().first()
        Truth.assertThat(tasks).doesNotContain(aNote)
    }

    @Test
    fun saveTask_taskProvided_UpdateSavedTask() = runBlocking {
        val aNote = Note(1, "Jhaman test", "update test")
        dao.insertNote(aNote)
        val newNote = aNote.copy(noteTitle = "Jhaman Das test")
        dao.updateNote(newNote)
        val tasks = dao.getAllNotes().first()
        Truth.assertThat(tasks).contains(newNote)
    }

    @Test
    fun saveTask_taskProvided_SearchSavedTask() = runBlocking {
        val aNote = Note(1, "Jhaman test", "searching")
        dao.insertNote(aNote)
        val tasks = dao.searchNote("%test%").first()
        Truth.assertThat(tasks).contains(aNote)
    }
}