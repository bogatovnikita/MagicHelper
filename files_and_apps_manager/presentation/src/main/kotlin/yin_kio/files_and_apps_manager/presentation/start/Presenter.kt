package yin_kio.files_and_apps_manager.presentation.start

import Yin_Koi.files_and_apps_manager.presentation.R
import android.content.Context
import android.text.format.Formatter.formatFileSize
import kotlin.math.roundToInt

internal class Presenter(
    private val context: Context
) {

    fun presentPercents(percents: Int) : String{
        return context.getString(R.string.file_and_apps_manager_percents, percents)
    }

    fun presentOccupiedAndTotal(occupied: Long, total: Long) : String{
        return context.getString(R.string.file_and_apps_manager_occupied_and_total,
            formatFileSize(context, occupied),
            formatFileSize(context, total)
        )
    }

    fun presentProgress(occupied: Long, total: Long) : Int{
        return ((occupied / total.toDouble()) * 100).roundToInt()
    }

}