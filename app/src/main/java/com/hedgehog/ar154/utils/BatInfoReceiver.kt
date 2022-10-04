package com.hedgehog.ar154.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.lifecycle.MutableLiveData
import com.hedgehog.ar154.AppClass
import java.lang.Exception

object BatInfoReceiver : BroadcastReceiver() {
    var temp = 0
    fun get_temp(): Float {
        return (temp / 10).toFloat()
    }

    private val batteryTemperature = MutableLiveData<Int>()

    override fun onReceive(arg0: Context, intent: Intent) {
        temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
        batteryTemperature.postValue(NativeProvider.calculateTemperature(arg0, temp))
    }

    fun getBatteryInfo() = batteryTemperature

    fun registerReceiver(activity: Activity){
        activity.registerReceiver(
            this,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
    }

    fun unregisterReceiver(activity: Activity){
        try{
            activity.unregisterReceiver(this)
        } catch (e: Exception){}
    }

    fun updateInfo() {
        batteryTemperature.postValue(NativeProvider.calculateTemperature(AppClass.instance, temp))
    }
}