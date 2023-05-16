package com.bogatovnikita.settings

import android.content.Context

class FunctionSettings(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(FUNCTION_SETTINGS, Context.MODE_PRIVATE)


    companion object {
        const val FUNCTION_SETTINGS = "FUNCTION_SETTINGS"
    }
}
