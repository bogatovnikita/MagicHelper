package ar.cleaner.first.pf.domain.models

data class CleanerDetails(
    val trashSize: Double,
    val usedMemorySize: Double,
    val totalSize: Double,
    val trashPercents: Int,
    val usedPercents: Int,
    val isOptimized: Boolean
)

