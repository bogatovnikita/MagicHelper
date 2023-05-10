package yin_kio.files_and_apps_manager.data

import android.content.Context
import file_manager.doman.overview.gateways.DeleteTimeSaver

class DeleteTimeSaverImpl(
    context: Context
) : DeleteTimeSaver {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveDeleteTime() {
        val currentTime = System.currentTimeMillis()
        prefs.edit().putLong("time", currentTime).apply()
    }

    companion object{
        private const val PREFS_NAME = "TIME"
    }

}