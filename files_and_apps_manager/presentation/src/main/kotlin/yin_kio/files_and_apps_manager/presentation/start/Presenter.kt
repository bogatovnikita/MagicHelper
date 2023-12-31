package yin_kio.files_and_apps_manager.presentation.start

import Yin_Koi.files_and_apps_manager.presentation.R
import android.content.Context
import android.text.format.Formatter.formatFileSize
import kotlin.math.roundToInt

private const val GB = 1073741824.0

internal class Presenter(
    private val context: Context
) {

    fun presentPercents(percents: Int): String {
        return context.getString(R.string.file_apps_manager_percents, percents)
    }

    fun presentOccupiedAndTotal(occupied: Long, total: Long): String {
        return context.getString(
            R.string.file_apps_manager_occupied_and_total,
            occupied.toDoubleStringWithOneDecimal(),
            total.toDoubleStringWithOneDecimal()
        )
    }

    fun presentProgress(occupied: Long, total: Long): Int {
        return ((occupied / total.toFloat() * 100)).toInt()
    }

    fun presentDangerColor(isCleaned: Boolean): Int {
        val colorId = if (isCleaned) {
            R.color.black
        } else {
            R.color.red
        }

        return context.getColor(colorId)
    }

    fun presentDangerText(isCleaned: Boolean): String {
        val strId = if (isCleaned) {
            R.string.file_apps_manager_start_already_freed
        } else {
            R.string.file_apps_manager_start_need_free
        }

        return context.getString(strId)
    }

    fun presentButtonBg(isCleaned: Boolean): Int {
        return if (isCleaned) {
            R.drawable.bg_button_cleaned
        } else {
            R.drawable.bg_button_danger
        }
    }

    private fun Long.toGb() = (this / GB)

    fun Long.toDoubleStringWithOneDecimal(): String {
        val doubleValue = this / GB
        return String.format("%.1f", doubleValue)
    }
}