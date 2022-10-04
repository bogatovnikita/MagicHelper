package com.hedgehog.ar154.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.hedgehog.ar154.AppClass
import com.hedgehog.ar154.data.RamUsageModel
import java.lang.Exception

object Utils {

    fun openUrl(context: Context, url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

    fun getUsageInfo(): RamUsageModel {
        val mi = ActivityManager.MemoryInfo()
        val activityManager = AppClass.instance.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)

        val ramUsage = NativeProvider.getRamUsage(AppClass.instance, mi.totalMem, mi.availMem)
        val ramTotal = mi.totalMem
        val ramFree = ramTotal - ramUsage
        val percentAvail = 100 - (ramFree * 100.0 / ramTotal)
        val hasLoad = NativeProvider.checkRamOverload(AppClass.instance)
        return RamUsageModel(
            percentAvail.toInt(),
            ramTotal / 1024.0 / 1024.0 / 1024.0,
            ((ramTotal - ramFree) / 1024.0 / 1024.0 / 1024.0),
            ramFree.toDouble() / 1024.0 / 1024.0 / 1024.0,
            hasLoad
        )
    }

    fun setScreenBrightness(value: Int){
        try{
            Settings.System.putInt(AppClass.instance.contentResolver, Settings.System.SCREEN_BRIGHTNESS, value)
        } catch (e: Exception){}
    }

}