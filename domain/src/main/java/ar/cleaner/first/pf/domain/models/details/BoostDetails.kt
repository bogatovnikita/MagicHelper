package ar.cleaner.first.pf.domain.models.details

data class BoostDetails(
    val usagePercents: Int = 0,
    val isOptimized: Boolean = false,
    val totalRam: Double = 0.0,
    val usedRam: Double = 0.0,
    val usedRamLong: Long = 0L
)
