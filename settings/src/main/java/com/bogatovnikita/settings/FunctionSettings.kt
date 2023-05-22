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

    fun saveLastUsageRam(value: Long) {
        sharedPreferences.edit().putLong(BOOST_LAST_USAGE_RAM, value).apply()
    }

    fun getLastUsageRam(): Long {
        return sharedPreferences.getLong(BOOST_LAST_USAGE_RAM,0L)
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

    fun saveTimeBatteryOptimization() =
        sharedPreferences.edit().putLong(BATTERY_TIME_OPTIMIZATION, System.currentTimeMillis())
            .apply()

    fun isBatteryOptimized(): Boolean {
        val currentTime = System.currentTimeMillis()
        val saveTime = sharedPreferences.getLong(BATTERY_TIME_OPTIMIZATION, 0L)
        return saveTime + TimeUnit.HOURS.toMillis(2) > currentTime
    }

    fun saveBatteryMode(mode: Int) =
        sharedPreferences.edit().putInt(BATTERY_MODE, mode)
            .apply()

    fun getBatteryMode(): Int =  sharedPreferences.getInt(BATTERY_MODE, BATTERY_MODE_NORMAL)

    companion object {

        const val FUNCTION_SETTINGS = "FUNCTION_SETTINGS"

        const val BOOST_STATUS = "BOOST_STATUS"
        const val BOOST_LAST_USAGE_RAM = "BOOST_LAST_USAGE_RAM"

        const val TEMPERATURE_DEGREE = "TEMPERATURE_DEGREE"
        const val TEMPERATURE_TIME_OPTIMIZATION = "TEMPERATURE_TIME_OPTIMIZATION"

        const val BATTERY_TIME_OPTIMIZATION = "TEMPERATURE_TIME_OPTIMIZATION"
        const val BATTERY_MODE = "BATTERY_MODE"
        const val BATTERY_MODE_NORMAL = 1111
        const val BATTERY_MODE_MEDIUM = 2222
        const val BATTERY_MODE_HIGH = 3333
    }

}
