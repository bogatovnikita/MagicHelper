package ar.cleaner.first.pf.domain.models.details

data class RamDetails(
    val usagePercents: Int,
    val totalRam: Double,
    val usedRam: Double,
    val isOptimized: Boolean
)
