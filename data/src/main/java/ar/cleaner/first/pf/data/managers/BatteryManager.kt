package ar.cleaner.first.pf.data.managers

import android.app.Application
import ar.cleaner.first.pf.data.local_receiver.batteryStateChangeFlow
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import ar.cleaner.first.pf.domain.const.Const
import ar.cleaner.first.pf.domain.utils.stateFlowReceiver
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class BatteryManager @Inject constructor(
    private val context: Application,
    private val defaultScope: CoroutineScope
) {

    private val batteryReceiverFlow = context
        .batteryStateChangeFlow()
        .stateFlowReceiver(scope = defaultScope, Const.INT_DEFAULT_VALUE)

    fun getBatteryPercents() = batteryReceiverFlow

}