package com.bogatovnikita.settings

import android.content.Context
import java.util.concurrent.TimeUnit

class FunctionSettingsImpl(context: Context) : FunctionSettings {

    private val sharedPreferences =
        context.getSharedPreferences(FUNCTION_SETTINGS, Context.MODE_PRIVATE)

    override fun saveBoostStatus() {
        sharedPreferences.edit().putLong(BOOST_STATUS, System.currentTimeMillis()).apply()
    }

    override fun getBoostStatus(): Boolean {
        val currentTime = System.currentTimeMillis()
        val saveTime = sharedPreferences.getLong(BOOST_STATUS, 0L)
        return saveTime + TimeUnit.DAYS.toMillis(1) > currentTime
    }

    companion object {
        const val FUNCTION_SETTINGS = "FUNCTION_SETTINGS"
        const val BOOST_STATUS = "BOOST_STATUS"
    }
}
