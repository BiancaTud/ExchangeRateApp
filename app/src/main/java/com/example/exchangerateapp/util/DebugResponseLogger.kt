package com.example.exchangerateapp.util

import android.util.Log
import com.example.exchangerateapp.BuildConfig

object DebugResponseLogger {
    private val formatOutput = "Request: %s\n RESPONSE: %s\n"
    private var tag = "API"

    fun log(request: String, response: String?) {
        if (!BuildConfig.DEBUG) {
            return
        }

        Log.d(tag, String.format(formatOutput, request, response))
    }
}