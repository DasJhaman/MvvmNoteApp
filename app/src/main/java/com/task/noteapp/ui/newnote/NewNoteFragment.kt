package com.task.noteapp.ui.newnote

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.task.noteapp.MainActivity
import com.task.noteapp.R
import com.task.noteapp.base.BaseFragment
import com.task.noteapp.databinding.FragmentNewNoteBinding
import com.task.noteapp.helper.showSnack
import com.task.noteapp.helper.toast
import com.task.noteapp.model.Note
import com.task.noteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteFragment : BaseFragment<FragmentNewNoteBinding>() {
    private val noteViewModel: NoteViewModel by viewModels()

    override fun getViewBinding() = FragmentNewNoteBinding.inflate(layoutInflater)
    private lateinit var mView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view

    }

    override fun onTitleChange() {
        (requireActivity() as MainActivity).title = getString(R.string.menu_new_note)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_new_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                saveNote(mView)
            }

            android.R.id.home -> {
                view?.findNavController()?.navigate(R.id.action_nav_new_note_to_nav_home)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote(view: View) {
        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()
        val imageUrl = binding.etNoteImage.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody, noteImage = imageUrl.ifEmpty { null })

            noteViewModel.addNote(note)
            view.showSnack(getString(R.string.note_saved_successfully))
            view.findNavController().navigate(NewNoteFragmentDirections.actionNavNewNoteToNavHome())

        } else {
            activity?.toast(getString(R.string.note_title_please))
        }
    }
}