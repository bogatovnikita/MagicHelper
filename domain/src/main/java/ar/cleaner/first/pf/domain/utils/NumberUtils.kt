package ar.cleaner.first.pf.domain.utils

import kotlin.math.roundToInt

fun sumAll(vararg elements: Double): Double {
    var result = 0.0
    elements.forEach {
        result += it
    }
    return result
}

fun Double.percents(total: Double): Int = ((this / total) * 100).roundToInt()
