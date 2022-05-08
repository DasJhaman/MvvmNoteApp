package com.task.noteapp.ui.updatenote

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.task.noteapp.MainActivity
import com.task.noteapp.R
import com.task.noteapp.base.BaseFragment
import com.task.noteapp.databinding.FragmentUpdateNoteBinding
import com.task.noteapp.helper.toast
import com.task.noteapp.model.Note
import com.task.noteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateNoteFragment : BaseFragment<FragmentUpdateNoteBinding>() {
    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private val noteViewModel: NoteViewModel by viewModels()


    override fun getViewBinding() = FragmentUpdateNoteBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentNote = args.note!!

        binding.etNoteBody.setText(currentNote.noteBody)
        binding.etNoteTitle.setText(currentNote.noteTitle)
        currentNote.noteImage?.let {
            binding.etNoteImage.setText(it)
        }

        binding.fabDone.setOnClickListener {
            val title = binding.etNoteTitle.text.toString().trim()
            val body = binding.etNoteBody.text.toString().trim()
            val noteImage = binding.etNoteImage.text.toString().trim()

            if (title.isNotEmpty()) {
                val note = Note(currentNote.id, title, body, true, noteImage.ifEmpty { null })
                noteViewModel.updateNote(note)
                view.findNavController()
                    .navigate(UpdateNoteFragmentDirections.actionNavUpdateNoteToNavHome())

            } else {
                activity?.toast(getString(R.string.note_title_please))
            }
        }

    }

    override fun onTitleChange() {
        (requireActivity() as MainActivity).title = getString(R.string.menu_update_note)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle(getString(R.string.delete_title))
            setMessage(getString(R.string.delete_note_confirmation_message))
            setPositiveButton(getString(R.string.delete)) { _, _ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                    R.id.action_nav_update_note_to_nav_home
                )
            }
            setNegativeButton(getString(R.string.cancel), null)
        }.create().show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
            }
            android.R.id.home -> {
                view?.findNavController()?.navigate(R.id.action_nav_update_note_to_nav_home)

            }
        }

        return super.onOptionsItemSelected(item)
    }
}