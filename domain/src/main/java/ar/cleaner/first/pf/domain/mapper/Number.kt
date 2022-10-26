package ar.cleaner.first.pf.domain.mapper

import ar.cleaner.first.pf.domain.models.BatteryTime
import kotlin.math.ceil
import kotlin.math.roundToInt

private const val MAX_MINUTES = 59
fun Double.asRemainingTime(): BatteryTime {
    var remainder = (this - this.toInt()) * 100
    if (remainder > MAX_MINUTES) {
        val difference = remainder - MAX_MINUTES
        remainder -= difference
    }
    val minutes = (remainder).roundToInt()
    val hours = this.roundToInt()

    return BatteryTime(hours, minutes)
}

fun Double.asPercents(total: Double): Int = ceil((this / total) * 100).roundToInt()

fun Double.asTrashPercents(total: Double): Int = ceil((this / total) * 100).roundToInt()

fun Int.asPercents(total: Int): Int = ceil((this.toDouble() / total) * 100).roundToInt()