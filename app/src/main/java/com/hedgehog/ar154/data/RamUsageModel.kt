package com.hedgehog.ar154.data

data class RamUsageModel(
    val percent: Int,
    val totalGb: Double,
    val usageGb: Double,
    val availableGb: Double,
    val hasOverload: Boolean,
    var percentsToFree: Int? = null
)
