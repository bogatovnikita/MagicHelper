package ar.cleaner.first.pf.data.settings.battery

import android.app.Application
import ar.cleaner.first.pf.domain.gateways.battery.BatterySettings
import ar.cleaner.first.pf.domain.models.BatteryMode
import com.bogatovnikita.settings.FunctionSettings
import com.bogatovnikita.settings.FunctionSettings.Companion.BATTERY_MODE_HIGH
import com.bogatovnikita.settings.FunctionSettings.Companion.BATTERY_MODE_MEDIUM
import com.bogatovnikita.settings.FunctionSettings.Companion.BATTERY_MODE_NORMAL
import javax.inject.Inject

class BatterySettingsImpl @Inject constructor(
    private val context: Application,
) : BatterySettings {

    private val settings = FunctionSettings(context)

    override fun saveTimeBatteryOptimization() = settings.saveTimeBatteryOptimization()

    override fun isBatteryOptimized(): Boolean = settings.isBatteryOptimized()

    override fun saveBatteryMode(mode: BatteryMode) {
        val mode = when (mode) {
            BatteryMode.NORMAL -> BATTERY_MODE_NORMAL
            BatteryMode.MEDIUM -> BATTERY_MODE_MEDIUM
            BatteryMode.HIGH -> BATTERY_MODE_HIGH
        }
        settings.saveBatteryMode(mode)
    }

    override fun getBatteryMode(): BatteryMode = when (settings.getBatteryMode()) {
        BATTERY_MODE_NORMAL -> BatteryMode.NORMAL
        BATTERY_MODE_MEDIUM -> BatteryMode.MEDIUM
        BATTERY_MODE_HIGH -> BatteryMode.HIGH
        else -> BatteryMode.NORMAL
    }
}