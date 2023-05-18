package com.bogatovnikita.settings

import android.content.Context
import java.util.concurrent.TimeUnit

class FunctionSettings(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(FUNCTION_SETTINGS, Context.MODE_PRIVATE)

    fun saveBoostStatus() {
        sharedPreferences.edit().putLong(BOOST_STATUS, System.currentTimeMillis()).apply()
    }

    fun getBoostStatus(): Boolean {
        val currentTime = System.currentTimeMillis()
        val saveTime = sharedPreferences.getLong(BOOST_STATUS, 0L)
        return saveTime + TimeUnit.DAYS.toMillis(1) > currentTime
    }

    fun saveTimeTemperatureOptimization() =
        sharedPreferences.edit().putLong(TEMPERATURE_TIME_OPTIMIZATION, System.currentTimeMillis())
            .apply()

    fun isTemperatureChecked(): Boolean {
        val currentTime = System.currentTimeMillis()
        val saveTime = sharedPreferences.getLong(TEMPERATURE_TIME_OPTIMIZATION, 0L)
        return saveTime + TimeUnit.HOURS.toMillis(2) > currentTime
    }

    fun saveLastTemperature(degree: Int) =
        sharedPreferences.edit().putInt(TEMPERATURE_DEGREE, degree).apply()

    fun gatLastTemperature(): Int = sharedPreferences.getInt(TEMPERATURE_DEGREE, -1)

    companion object {
        const val FUNCTION_SETTINGS = "FUNCTION_SETTINGS"
        const val BOOST_STATUS = "BOOST_STATUS"
        const val TEMPERATURE_DEGREE = "TEMPERATURE_DEGREE"
        const val TEMPERATURE_TIME_OPTIMIZATION = "TEMPERATURE_TIME_OPTIMIZATION"
    }

}
