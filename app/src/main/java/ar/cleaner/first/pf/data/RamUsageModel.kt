package ar.cleaner.first.pf.data

data class RamUsageModel(
    val percent: Int,
    val totalGb: Double,
    val usageGb: Double,
    val availableGb: Double,
    val hasOverload: Boolean,
    var percentsToFree: Int? = null
)
