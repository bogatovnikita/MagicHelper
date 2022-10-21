package ar.cleaner.first.pf.domain.utils

fun getCurrentTime() = System.currentTimeMillis()

fun isTimePassed(lastTimeMillis: Long): Boolean =
    (getCurrentTime() > (lastTimeMillis + FIFTEEN_MINUTES)) || lastTimeMillis == 0L

const val FIFTEEN_MINUTES: Long = 1000 * 60 * 15