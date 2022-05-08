package com.task.noteapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.databinding.SingleNoteItemBinding
import com.task.noteapp.helper.loadImage
import com.task.noteapp.helper.visible
import com.task.noteapp.model.Note
import com.task.noteapp.ui.home.HomeFragmentDirections


class NoteAdapter(
    private val context: Context) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val itemBinding: SingleNoteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.noteBody == newItem.noteBody &&
                        oldItem.noteTitle == newItem.noteTitle &&
                        oldItem.isNoteEdited == newItem.isNoteEdited &&
                        oldItem.noteImage == newItem.noteImage
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            SingleNoteItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]
        holder.itemBinding.tvNoteBody.text = currentNote.noteBody
        holder.itemBinding.tvCreatedDate.formattedDate(currentNote.createDate)

        if (currentNote.isNoteEdited)
            holder.itemBinding.tvNoteTitle.text =
                String.format(context.getString(R.string.note_edited), currentNote.noteTitle)
        else
            holder.itemBinding.tvNoteTitle.text = currentNote.noteTitle

        currentNote.noteImage?.let {
            holder.itemBinding.noteImage.visible()
            holder.itemBinding.noteImage.loadImage(it)
        }

        holder.itemView.setOnClickListener { view ->
            val direction = HomeFragmentDirections
                .actionNavHomeToNavUpdateNote(currentNote)
            view.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}