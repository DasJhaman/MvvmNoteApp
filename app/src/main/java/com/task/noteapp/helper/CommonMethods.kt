package com.task.noteapp.helper

import java.text.SimpleDateFormat
import java.util.*

class CommonMethods {

    companion object {
        fun getFormattedDate(longValue: Long): String {
            val sdf = SimpleDateFormat(Constants.DATE_FORMATTER, Locale.getDefault())
            val netDate = Date(longValue)
            return sdf.format(netDate)
        }
    }

}