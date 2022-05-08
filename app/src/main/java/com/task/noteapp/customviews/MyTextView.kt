package com.task.noteapp.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.task.noteapp.helper.CommonMethods

class MyTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {


    fun formattedDate(longValue: Long) {
        val text = CommonMethods.getFormattedDate(longValue)
        this.text = CommonMethods.getFormattedDate(longValue)
    }
}