package ar.cleaner.first.pf.domain.models.details

import ar.cleaner.first.pf.domain.models.BatteryMode

data class BatteryDetails(
    val batteryCharge: Int,
    val batteryMode: BatteryMode,
    val batteryRemainingTime: String,
    val isOptimized: Boolean,
)