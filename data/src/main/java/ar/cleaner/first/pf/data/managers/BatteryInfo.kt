package ar.cleaner.first.pf.data.managers

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import ar.cleaner.first.pf.data.local_receiver.batteryStateChangeFlow
import ar.cleaner.first.pf.domain.const.Const
import ar.cleaner.first.pf.domain.models.details.CAN_NOT_CALCULATE_TIME
import ar.cleaner.first.pf.domain.utils.lazyStateFlow
import ar.cleaner.first.pf.domain.utils.stateFlowReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

class BatteryInfo @Inject constructor(
    private val context: Application,
    private val defaultScope: CoroutineScope
) {

    private val batteryReceiverFlow = context
        .batteryStateChangeFlow()
        .stateFlowReceiver(scope = defaultScope, Const.INT_DEFAULT_VALUE)

    fun getBatteryPercents() = batteryReceiverFlow

    fun getTimeToFullCharge() = lazyStateFlow(
        scope = defaultScope,
        defaultValue = CAN_NOT_CALCULATE_TIME
    ) {
        while (true) {
            trySend(getTime())
            delay(3000)
        }
    }

    private val batteryManager =
        context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

    private fun getTime(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            batteryManager.computeChargeTimeRemaining().toInt()
        } else {
            CAN_NOT_CALCULATE_TIME
        }
    }

    private val batteryStatusIntent: Intent?
        get() {
            val batFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            return context.registerReceiver(null, batFilter)
        }

    private val chargingSource: Boolean
        get() {
            val intent = batteryStatusIntent
            val plugged = intent!!.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
            return when (plugged) {
                BatteryManager.BATTERY_PLUGGED_AC -> true
                BatteryManager.BATTERY_PLUGGED_USB -> true
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> true
                else -> false
            }
        }

    fun isCharging(): Boolean = chargingSource

}