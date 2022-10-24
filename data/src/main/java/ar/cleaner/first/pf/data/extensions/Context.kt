package ar.cleaner.first.pf.data.extensions

import android.app.ActivityManager
import android.app.usage.StorageStatsManager
import android.app.usage.UsageStatsManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.roundToInt

val Context.storageStatsManager: StorageStatsManager
    @RequiresApi(Build.VERSION_CODES.O)
    get() = getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager

val Context.bluetoothAdapter: BluetoothAdapter
    get() = (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

val Context.wifiManager: WifiManager
    get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

val Context.activityManager get() = getSystemService(ACTIVITY_SERVICE) as ActivityManager

val Context.connectivityManager get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

val Context.batteryManager get() = getSystemService(Context.BATTERY_SERVICE) as BatteryManager

fun Context.getAppsOnPhone() =
    packageManager.getInstalledApplications(PackageManager.GET_META_DATA).filter {
        it.isSystem()
    }

val Context.usageStatsManager: UsageStatsManager
    get() = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

fun Context.getBatteryCapacity(): Int {
    val chargeCounter =
        batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
    val capacity: Int = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

    return ((chargeCounter / capacity) * 0.001).roundToInt()
}

fun ApplicationInfo.isSystem(): Boolean =
    this.flags and ApplicationInfo.FLAG_SYSTEM == 0

fun Context.isSystem(packageName: String): Boolean =
    packageManager.getApplicationInfo(packageName.trim(), 0).isSystem()

fun Context.getAppName(packageName: String): String {
    return packageManager.getApplicationLabel(
        packageManager.getApplicationInfo(packageName, 0)
    ).toString()
}
