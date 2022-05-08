package com.task.noteapp.viewmodel

import androidx.lifecycle.viewModelScope
import com.task.noteapp.model.Note
import com.task.noteapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : BaseViewModel<NoteViewModel.NotesViewEvent>() {

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }

    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }

    fun getAllNote() {
        viewModelScope.launch {
            noteRepository.getAllNotes().collect {
                emitViewEvent(NotesViewEvent.NoteFetched(it))
            }
        }
    }

    fun searchNote(query: String?) {
        viewModelScope.launch {
            noteRepository.searchNote(query).collect {
                emitViewEvent(NotesViewEvent.NoteFetched(it))
            }
        }
    }

    sealed class NotesViewEvent : ViewEvent() {
        data class NoteFetched(val notes: List<Note>) : NotesViewEvent()
    }

}