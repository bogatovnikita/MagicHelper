package ar.cleaner.first.pf.utils

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build

class WifiSwitch(private val context: Context) {

    fun disable() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            wifiManager().isWifiEnabled = false
        }
    }

    private fun wifiManager() =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
}