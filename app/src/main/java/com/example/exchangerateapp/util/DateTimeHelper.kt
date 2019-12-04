package com.example.exchangerateapp.util

import java.text.DateFormat
import java.util.*


fun getDateFormat(timestamp: Long): String {
    val date = Date(timestamp)
    return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM).format(date)
}