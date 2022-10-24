package ar.cleaner.first.pf.data.managers

import android.app.Application
import ar.cleaner.first.pf.data.extensions.getBatteryCapacity
import ar.cleaner.first.pf.data.local_receiver.batteryStateChangeFlow
import ar.cleaner.first.pf.data.local_receiver.bluetoothStateChangeFlow
import ar.cleaner.first.pf.data.local_receiver.brightnessStateChangeFlow
import ar.cleaner.first.pf.data.local_receiver.wifiStateChangeFlow
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import ar.cleaner.first.pf.domain.const.Const
import ar.cleaner.first.pf.domain.const.Const.DEFAULT_BRIGHTNESS_LEVEL
import ar.cleaner.first.pf.domain.utils.isWorking
import ar.cleaner.first.pf.domain.utils.stateFlowReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log10

class BatteryManager @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val context: Application,
    private val defaultScope: CoroutineScope
) {
    private val wifiReceiverFlow = context
        .wifiStateChangeFlow()
        .stateFlowReceiver(scope = defaultScope, Const.BOOL_DEFAULT_VALUE)

    private val batteryReceiverFlow = context
        .batteryStateChangeFlow()
        .stateFlowReceiver(scope = defaultScope, Const.INT_DEFAULT_VALUE)

    private val bluetoothReceiverFlow = context
        .bluetoothStateChangeFlow()
        .stateFlowReceiver(scope = defaultScope, Const.BOOL_DEFAULT_VALUE)

    private val brightnessObserverFlow = context
        .brightnessStateChangeFlow()
        .stateFlowReceiver(scope = defaultScope, DEFAULT_BRIGHTNESS_LEVEL)

    fun getBatteryPercents() = batteryReceiverFlow

    fun getBatteryMode() = preferencesManager.batteryModeFlow

    fun getRemainingChargeTime(): Flow<Double> = flow {
        while (isWorking()) {
            val brightness = brightnessObserverFlow.first()
            val isWifiOn = wifiReceiverFlow.first()
            val isBluetoothOn = bluetoothReceiverFlow.first()
            val batteryPercents = batteryReceiverFlow.first()

            val batteryCapacity = withContext(currentCoroutineContext()) {
                context.getBatteryCapacity()
            }

            val remainingTime = calculateRemaining(
                loadings = intArrayOf(
                    checkWifiAndReturnValue(isWifiOn),
                    checkBluetoothAndReturnValue(isBluetoothOn),
                    brightness
                ),
                capacity = (batteryCapacity * batteryPercents) / 60
            )
            emit(remainingTime)
            delay(3000)
        }
    }
        .cancellable()

    private fun checkWifiAndReturnValue(isOn: Boolean): Int {
        return if (isOn) WIFI_ON_VALUE
        else WIFI_OFF_VALUE
    }

    private fun checkBluetoothAndReturnValue(isOn: Boolean): Int {
        return if (isOn) BLUETOOTH_ON_VALUE
        else BLUETOOTH_OFF_VALUE
    }

    private fun calculateRemaining(vararg loadings: Int, capacity: Int): Double {
        val multiplyLoadings = loadings.reduce { prev, next ->
            prev * next
        }.toDouble()

        val sumLoadings = loadings.reduce { prev, next ->
            prev + next
        }.toDouble()

        return capacity / log10(multiplyLoadings * sumLoadings)
    }

    companion object {
        private const val WIFI_ON_VALUE: Int = 10
        private const val WIFI_OFF_VALUE: Int = 1

        private const val BLUETOOTH_ON_VALUE: Int = 7
        private const val BLUETOOTH_OFF_VALUE: Int = 1
    }
}