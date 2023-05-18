package ar.cleaner.first.pf.domain.extencion

import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.Garbage
import ar.cleaner.first.pf.domain.models.details.TemperatureDetails
import ar.cleaner.first.pf.domain.models.details.RamDetails

fun CleanerDetails.isValuesCompatible(): Boolean =
    (totalSize > 0.0 || usedMemorySize > 0.0)

fun TemperatureDetails.isValuesCompatible(): Boolean = temperature > 0.0

fun RamDetails.isValuesCompatible(): Boolean =
    usedRam > 0 && totalRam > 0 && usagePercents > 0

fun <T> Garbage<T>.isValuesCompatible(): Boolean = totalGarbageSize > 0 || !list.isNullOrEmpty()