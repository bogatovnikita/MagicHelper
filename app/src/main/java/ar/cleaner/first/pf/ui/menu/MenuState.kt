package ar.cleaner.first.pf.ui.menu

data class MenuState(
    val batteryCharge: Int = 0,
    val isBatteryOptimized: Boolean = false,
    val usageRamPercents: Float = 0f,
    val totalRam: Double = 0.0,
    val usedRam: Double = 0.0,
    val isRamOptimized: Boolean = false,
    val isTemperatureChecked: Boolean = false,
    val usedMemorySize: Double = 0.0,
    val totalSize: Double = 0.0,
    val usageMemoryPercents: Int = 0,
    val isMemoryOptimized: Boolean = false,
)