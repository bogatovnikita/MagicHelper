package ar.cleaner.second.pf.ui.menu

data class MenuState(
    val batteryCharge: Int = 0,
    val isBatteryOptimized: Boolean = false,
    val isNeedShowTimeToFullCharge: Boolean = false,
    val timeToFullCharge: Pair<Int, Int> = 0 to 0,
    val usageRamPercents: Float = 63f,
    val totalRam: Double = 0.0,
    val usedRam: Double = 0.0,
    val isRamOptimized: Boolean = false,
    val isTemperatureChecked: Boolean = false,
    val usedMemorySize: Double = 0.0,
    val totalSize: Double = 0.0,
    val usageMemoryPercents: Int = 65,
    val isMemoryOptimized: Boolean = false,
    val isChargingNow: Boolean = false
)