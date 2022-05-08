package com.task.noteapp.ui.home

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.task.noteapp.MainActivity
import com.task.noteapp.R
import com.task.noteapp.adapter.NoteAdapter
import com.task.noteapp.base.BaseFragment
import com.task.noteapp.databinding.FragmentHomeBinding
import com.task.noteapp.helper.gone
import com.task.noteapp.helper.visible
import com.task.noteapp.model.Note
import com.task.noteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), SearchView.OnQueryTextListener {
    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_home_to_nav_new_note)
        }
    }

    override fun onTitleChange() {
        (requireActivity() as MainActivity).title = getString(R.string.menu_home)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onResume() {
        super.onResume()
        onTitleChange()
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter(requireContext())
        binding.recyclerView.adapter = noteAdapter
        activity?.let {
            observeResponse()
            noteViewModel.getAllNote()
        }
    }

    private fun observeResponse() {
        noteViewModel.eventsFlow.onEach { notesViewEvent ->
            when (notesViewEvent) {
                is NoteViewModel.NotesViewEvent.NoteFetched -> {
                    noteAdapter.differ.submitList(notesViewEvent.notes)
                    updateUI(notesViewEvent.notes)
                }
            }

        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateUI(note: List<Note>) {
        if (note.isNotEmpty()) {
            binding.cardView.gone()
            binding.recyclerView.visible()
        } else {
            binding.cardView.visible()
            binding.recyclerView.gone()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)
        val searchView: SearchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(text: String?): Boolean {
        text?.let {
            searchNote(it)
        }
        return true
    }

    private fun searchNote(query: String?) {
        val searchQuery = "%$query%"
        noteViewModel.searchNote(searchQuery)
    }
}