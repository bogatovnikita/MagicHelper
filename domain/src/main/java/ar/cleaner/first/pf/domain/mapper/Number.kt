package ar.cleaner.first.pf.domain.mapper

import kotlin.math.ceil
import kotlin.math.roundToInt

fun Int.asPercents(total: Int): Int = ceil((this.toDouble() / total) * 100).roundToInt()