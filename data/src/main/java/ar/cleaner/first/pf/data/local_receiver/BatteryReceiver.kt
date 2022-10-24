package ar.cleaner.first.pf.data.local_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import ar.cleaner.first.pf.domain.const.Const
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable
import kotlin.math.roundToInt

val batteryIntentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED).apply {
    priority = Const.MAX_PRIORITY
}

fun Context.batteryStateChangeFlow() = callbackFlow {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            trySend(
                (level * 100 / scale.toFloat()).roundToInt()
            )
        }
    }

    registerReceiver(receiver, batteryIntentFilter)

    awaitClose {
        unregisterReceiver(receiver)
    }
}.cancellable()
