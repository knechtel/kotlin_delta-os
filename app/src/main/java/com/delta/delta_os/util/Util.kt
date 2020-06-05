package com.delta.delta_os.util

import java.text.SimpleDateFormat
import java.util.*

class Util {
    fun Date.toStringDate(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    public fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
    fun toSimpleString(date: Date) : String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(date)
    }


}