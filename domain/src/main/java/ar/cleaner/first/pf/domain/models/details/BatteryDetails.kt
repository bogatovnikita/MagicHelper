package ar.cleaner.first.pf.domain.models.details

import ar.cleaner.first.pf.domain.models.BatteryMode

data class BatteryDetails(
    val batteryCharge: Int = 53,
    val batteryMode: BatteryMode = BatteryMode.NORMAL,
    val isOptimized: Boolean = false,
    val batteryListFun: List<String> = emptyList(),
    val timeToFullCharge: Int = CAN_NOT_CALCULATE_TIME,
    val isCharging: Boolean = false,
)

const val CAN_NOT_CALCULATE_TIME = -1