package ar.cleaner.first.pf.data.clean_checker

import android.content.Context
import ar.cleaner.first.pf.data.PrefNames
import dagger.hilt.android.qualifiers.ApplicationContext
import yin_kio.clean_checker.LastCleanTime
import javax.inject.Inject

class LastCleanTimeImpl @Inject constructor(
    @ApplicationContext context: Context
) : LastCleanTime {

    private val prefs = context.getSharedPreferences(PrefNames.CLEAN_CHECKER_PREFS, Context.MODE_PRIVATE)

    override fun provide(): Long {
        return prefs.getLong("time", 0)
    }

}