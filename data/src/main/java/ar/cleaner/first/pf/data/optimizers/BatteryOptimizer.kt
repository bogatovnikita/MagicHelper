package ar.cleaner.first.pf.data.optimizers

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.provider.Settings
import ar.cleaner.first.pf.data.extensions.bluetoothAdapter
import ar.cleaner.first.pf.data.extensions.wifiManager
import ar.cleaner.first.pf.data.providers.kill_background_processes.KillBackgroundProcessesProvider
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.utils.emitProgressWithDelay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BatteryOptimizer @Inject constructor(
    private val context: Application,
    private val scope: CoroutineScope,
    private val processes: KillBackgroundProcessesProvider,
) {

    fun runOptimization(mode: BatteryMode): Flow<Int> =
        when (mode) {
            BatteryMode.NORMAL -> lowOptimizing()
            BatteryMode.MEDIUM -> mediumOptimizing()
            BatteryMode.HIGH -> highOptimizing()
        }

    /**
     * Decrease brightness to 30%, turn off bluetooth, wifi, turn off auto-brightness
     */
    private fun highOptimizing(): Flow<Int> = flow {
        killBackgroundProcesses()
        emitProgressWithDelay(from = 0, to = 20); setBrightnessLevel(1)
        emitProgressWithDelay(from = 20, to = 40); turnOffAutoBrightness()
        emitProgressWithDelay(from = 40, to = 60); turnOffBluetooth()
        emitProgressWithDelay(from = 60, to = 70); turnOffWifi()
        emitProgressWithDelay(from = 70, to = 100)
    }

    /**
     * Decrease brightness to 50%, turn off bluetooth, turn off wifi
     */
    private fun mediumOptimizing(): Flow<Int> = flow {
        killBackgroundProcesses()
        emitProgressWithDelay(from = 0, to = 50);
        setBrightnessLevel(7)
        turnOffAutoBrightness()
        emitProgressWithDelay(from = 50, to = 73); turnOffBluetooth()
        emitProgressWithDelay(from = 73, to = 89); turnOffWifi()
        emitProgressWithDelay(from = 89, to = 100)
    }

    /**
     * Decrease brightness to 70%
     */
    private fun lowOptimizing(): Flow<Int> = flow {
        emitProgressWithDelay(from = 0, to = 53)
        turnOffAutoBrightness()
        setBrightnessLevel(20)
        emitProgressWithDelay(from = 53, to = 100)
    }

    private fun setBrightnessLevel(level: Int) {
        try {
            Settings.System.putInt(
                context.contentResolver, Settings.System.SCREEN_BRIGHTNESS,
                level
            )
        } catch (e: Exception) {
            e.printStackTrace()
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

    private fun killBackgroundProcesses() {
        scope.launch {
            processes.killBackGroundProcessesSystemApps()
        }
        scope.launch {
            processes.killBackGroundProcessesInstalledApps()
        }
    }

}