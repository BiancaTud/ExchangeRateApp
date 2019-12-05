package com.example.exchangerateapp.util

import com.example.exchangerateapp.di.API_DATE_FORMAT
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


fun getLongDateFormat(timestamp: Long): String {
    val date = Date(timestamp)
    return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM).format(date)
}


fun getDateFormatForApi(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat(API_DATE_FORMAT);
    return format.format(date)
}

fun getStartDate(days: Int): String {
    val calendar = addBusinessDay(Calendar.getInstance(), -days)
    return getDateFormatForApi(calendar?.timeInMillis?: Calendar.getInstance().timeInMillis )
}


private fun addBusinessDay(cal: Calendar?, numBusinessDays: Int?): Calendar? {
    if (cal == null || numBusinessDays == null || numBusinessDays.toInt() == 0) {
        return cal
    }
    val numDays = abs(numBusinessDays.toInt())
    val dateAddition = if (numBusinessDays.toInt() < 0) -1 else 1
    var businessDayCount = 0
    while (businessDayCount < numDays) {
        cal.add(Calendar.DATE, dateAddition)

        //check weekend
        if (!isWorkingDay(cal)) {
            continue//adds another day
        }

        businessDayCount++
    }
    return cal
}

private fun isWorkingDay(cal: Calendar): Boolean {
    val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    return !(dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
}


