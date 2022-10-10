package ar.cleaner.first.pf.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

object BatteryChargeReceiver : BroadcastReceiver() {

    private val batteryPercent = MutableLiveData<Int>()

    override fun onReceive(ctxt: Context?, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = level * 100 / scale.toFloat()
        batteryPercent.postValue(batteryPct.toInt())
    }

    fun getInfo() = batteryPercent

    fun registerReceiver(activity: Activity) {
        activity.registerReceiver(
            this,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
    }

    fun unregisterReceiver(activity: Activity) {
        try {
            activity.unregisterReceiver(this)
        } catch (e: Exception){}
    }
}