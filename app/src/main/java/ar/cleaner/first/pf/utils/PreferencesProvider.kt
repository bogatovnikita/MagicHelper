package ar.cleaner.first.pf.utils

import android.content.Context
import ar.cleaner.first.pf.AppClass

object PreferencesProvider {

    private val PREFERENCES_NAME = "preferences"
    private val sharedPreferences =
        AppClass.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    private const val FIRST_LAUNCH = "first_launch"
    private const val BATTERY_TYPE = "battery_type"

    fun checkFirstLaunch() = sharedPreferences.getBoolean(FIRST_LAUNCH, true)
    fun saveNotFirstLaunch() {
        sharedPreferences.edit().putBoolean(FIRST_LAUNCH, false).apply()
    }

    fun saveToPreferences(fieldName: String, time: Long) {
        sharedPreferences.edit().putLong(fieldName, time).apply()
    }

    fun saveBatteryType(type: String) {
        sharedPreferences.edit().putString(BATTERY_TYPE, type).apply()
    }

    fun getBatteryType() = sharedPreferences.getString(BATTERY_TYPE, "")!!

}