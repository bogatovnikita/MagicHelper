package ar.cleaner.first.pf.domain.models.details

import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.models.BatteryTime

data class BatteryDetails(
    val batteryCharge: Int,
    val batteryMode: BatteryMode,
    val batteryRemainingTime: BatteryTime,
    val isOptimized: Boolean,
)