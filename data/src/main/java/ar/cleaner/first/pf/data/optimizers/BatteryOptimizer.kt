package ar.cleaner.first.pf.data.optimizers

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.provider.Settings
import ar.cleaner.first.pf.data.extensions.bluetoothAdapter
import ar.cleaner.first.pf.data.extensions.wifiManager
import ar.cleaner.first.pf.data.optimizers.base.BaseOptimizer
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.utils.emitProgressWithDelay
import ar.cleaner.first.pf.domain.utils.emulateProgressWorkFlow
import ar.cleaner.first.pf.domain.utils.isWorking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class BatteryOptimizer @Inject constructor(
    private val preferencesManager: PreferencesManager,
    val context: Application
) :
    BaseOptimizer() {
    override val lastOptimizationWorkMillis: StateFlow<Long>
        get() = preferencesManager.batteryLastOptimizationFlow

    override fun setLastOptimizationTime(time: Long) {
        preferencesManager.batteryLastOptimizationMillis = time
    }

    fun runOptimization(mode: BatteryMode): Flow<Int> =
        when (mode) {
            BatteryMode.NORMAL -> lowOptimizing()
            BatteryMode.MEDIUM -> mediumOptimizing()
            BatteryMode.HIGH -> highOptimizing()
        }

    fun emulateOptimization(mode: BatteryMode): Flow<Int> = emulateProgressWorkFlow().onCompletion {
        preferencesManager.batteryMode = mode.name
    }

    /**
     * Decrease brightness to 30%, turn off bluetooth, wifi, turn off auto-brightness
     */
    private fun highOptimizing(): Flow<Int> = flow {

        emitProgressWithDelay(from = 0, to = 20); setBrightnessLevel(30)
        emitProgressWithDelay(from = 20, to = 40); turnOffAutoBrightness()
        emitProgressWithDelay(from = 40, to = 60); turnOffBluetooth()
        emitProgressWithDelay(from = 60, to = 70); turnOffWifi()
        emitProgressWithDelay(from = 70, to = 100)
    }.onCompletion {
        if (isWorking()) preferencesManager.batteryMode = BatteryMode.HIGH.name
    }

    /**
     * Decrease brightness to 50%, turn off bluetooth, turn off wifi
     */
    private fun mediumOptimizing(): Flow<Int> = flow {
        emitProgressWithDelay(from = 0, to = 50); setBrightnessLevel(50)
        emitProgressWithDelay(from = 50, to = 73); turnOffBluetooth()
        emitProgressWithDelay(from = 73, to = 89); turnOffWifi()
        emitProgressWithDelay(from = 89, to = 100)
    }.onCompletion {
        if (isWorking()) preferencesManager.batteryMode = BatteryMode.MEDIUM.name
    }

    /**
     * Decrease brightness to 70%
     */
    private fun lowOptimizing(): Flow<Int> = flow {
        emitProgressWithDelay(from = 0, to = 53)
        setBrightnessLevel(70)
        emitProgressWithDelay(from = 53, to = 100)
    }.onCompletion {
        if (isWorking()) preferencesManager.batteryMode = BatteryMode.NORMAL.name
    }

    private fun setBrightnessLevel(level: Int) {
        if (Settings.System.canWrite(context)) {
            if (Settings.System.getInt(
                    context.contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS
                ) <= level * 10
            ) return
            Settings.System.putInt(
                context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, level * 10
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun turnOffBluetooth() {
        with(context) {
            if (bluetoothAdapter.isEnabled) bluetoothAdapter.disable()
        }
    }

    /**
     * Can't turning off wifi, if version is less then Q
     */
    private fun turnOffWifi() {
        with(context) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                if (wifiManager.isWifiEnabled) wifiManager.isWifiEnabled = false
        }
    }

    private fun turnOffAutoBrightness() {
        if (Settings.System.canWrite(context)) {
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
        }
    }
}