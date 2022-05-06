package com.task.noteapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.task.noteapp.helper.Constants
import kotlinx.android.parcel.Parcelize

@Entity(tableName = Constants.TABLE_NOTE)
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteBody: String,
    val isNoteEdited: Boolean = false,
    val noteImage: String? = null,
    val createDate: Long = System.currentTimeMillis()
) : Parcelable
