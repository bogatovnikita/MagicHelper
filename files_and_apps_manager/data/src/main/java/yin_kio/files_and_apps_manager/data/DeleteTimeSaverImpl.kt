package yin_kio.files_and_apps_manager.data

import android.content.Context
import ar.cleaner.first.pf.data.PrefNames
import ar.cleaner.first.pf.data.clean_checker.LastCleanTimeImpl
import file_manager.doman.overview.gateways.DeleteTimeSaver
import yin_kio.clean_checker.LastCleanTime

class DeleteTimeSaverImpl(
    context: Context
) : LastCleanTime by LastCleanTimeImpl(context), DeleteTimeSaver {

    private val prefs = context.getSharedPreferences(PrefNames.CLEAN_CHECKER_PREFS, Context.MODE_PRIVATE)

    override suspend fun saveDeleteTime() {
        val currentTime = System.currentTimeMillis()
        prefs.edit().putLong("time", currentTime).apply()
    }

}