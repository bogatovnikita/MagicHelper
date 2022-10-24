package ar.cleaner.first.pf.data.local_receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import ar.cleaner.first.pf.data.extensions.connectivityManager
import ar.cleaner.first.pf.domain.const.Const
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.cancellable

fun Context.wifiStateChangeFlow() = callbackFlow {

    val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            context ?: return
            intent ?: return

            val connectivityManager = context.connectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val nw = connectivityManager.activeNetwork
                val actNw = connectivityManager.getNetworkCapabilities(nw)
                if (actNw == null) {
                    trySend(true)
                } else {
                    trySendBlocking(!actNw.checkConnect())
                }
            } else {
                trySendBlocking(connectivityManager.activeNetworkInfo?.isConnected ?: false)
            }
        }
    }

    registerReceiver(receiver, wifiIntentFilter)

    awaitClose {
        unregisterReceiver(receiver)
    }
}.cancellable()

private fun NetworkCapabilities.checkConnect(): Boolean =
    this.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

val wifiIntentFilter = IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION).apply {
    priority = Const.MAX_PRIORITY
}
