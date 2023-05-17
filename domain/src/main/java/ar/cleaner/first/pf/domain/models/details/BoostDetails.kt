package ar.cleaner.first.pf.domain.models.details

data class BoostDetails(
    val usagePercents: Int = 0,
    val totalRam: Double = 0.0,
    val usedRam: Double = 0.0,
    val releasedMemory: Double = 0.0,
    val releasedPercent: Int = 0
)
