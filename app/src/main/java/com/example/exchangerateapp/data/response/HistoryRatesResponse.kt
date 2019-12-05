package com.example.exchangerateapp.data.response

import java.util.*

data class HistoryRatesResponse (val rates: Map<Date, Map<String, Double>>, val base: String, val date: String)