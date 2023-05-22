package ar.cleaner.first.pf.data.clean_checker

import android.content.Context
import ar.cleaner.first.pf.data.PrefNames
import yin_kio.clean_checker.LastCleanTime

class LastCleanTimeImpl(
    context: Context
) : LastCleanTime {

    private val prefs = context.getSharedPreferences(PrefNames.CLEAN_CHECKER_PREFS, Context.MODE_PRIVATE)

    override fun provide(): Long {
        return prefs.getLong("time", 0)
    }

}