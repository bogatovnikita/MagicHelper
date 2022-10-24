package ar.cleaner.first.pf.data.local_receiver

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable

val bluetoothIntentFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)

fun Context.bluetoothStateChangeFlow() = callbackFlow<Boolean> {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            with(intent) {
                if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {

                    val state = getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)

                    when (state) {
                        BluetoothAdapter.STATE_OFF -> trySendBlocking(false)
                        BluetoothAdapter.STATE_ON -> trySendBlocking(true)
                        BluetoothAdapter.STATE_TURNING_OFF -> trySendBlocking(false)
                        BluetoothAdapter.STATE_TURNING_ON -> trySendBlocking(true)
                    }
                }
            }
        }
    }

    registerReceiver(receiver, bluetoothIntentFilter)

    awaitClose {
        unregisterReceiver(receiver)
    }
}.cancellable()
