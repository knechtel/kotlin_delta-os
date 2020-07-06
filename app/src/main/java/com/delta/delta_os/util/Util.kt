package com.delta.delta_os.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class Util {
    fun Date.toStringDate(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    public fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun toSimpleString(date: Date): String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(date)
    }

    //@RequiresApi(Build.VERSION_CODES.O)
    fun toDate(string: String): Date? {

        if (string.equals("dataSaida")) {
            val date2 = SimpleDateFormat("dd/MM/yyyy")
            return  null;
        }
        if (string.equals("")) {
            val date2 = SimpleDateFormat("dd/MM/yyyy")
            return  null;
        }
        if (string.equals("dataEntrada")){
        //    return Date()
            val date2 = SimpleDateFormat("dd/MM/yyyy").parse("01/01/2020")
            return  null;
        }
        val date2 = SimpleDateFormat("dd/MM/yyyy").parse(string)

        return date2;
    }


}