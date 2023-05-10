package yin_kio.files_and_apps_manager.data

import android.content.Context
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.start_screen.gateways.LastCleanTime

class DeleteTimeSaverImpl(
    context: Context
) : DeleteTimeSaver, LastCleanTime {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveDeleteTime() {
        val currentTime = System.currentTimeMillis()
        prefs.edit().putLong("time", currentTime).apply()
    }

    override fun provide(): Long {
        return prefs.getLong("time", 0)
    }

    companion object{
        private const val PREFS_NAME = "TIME"
    }

}