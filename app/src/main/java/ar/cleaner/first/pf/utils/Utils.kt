package ar.cleaner.first.pf.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import ar.cleaner.first.pf.AppClass
import ar.cleaner.first.pf.data.RamUsageModel
import ar.cleaner.first.pf.data.StorageMemoryModel

object Utils {

    fun openUrl(context: Context, url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

    fun getUsageInfo(): RamUsageModel {
        val mi = ActivityManager.MemoryInfo()
        val activityManager =
            AppClass.instance.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
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

    fun getMemoryStorage(): StorageMemoryModel {
        val path = Environment.getExternalStorageDirectory()
        val stat = StatFs(path.path)
        val fakeOccupied: Double = (OptimizationProvider.getGarbageSize()).toDouble() / 1024.0
        val totalStorageMemory =
            (stat.blockSizeLong * stat.blockCountLong).toDouble() / (1024.0 * 1024.0 * 1024.0)
        val occupiedStorageMemory =
            (totalStorageMemory + fakeOccupied) - ((stat.blockSizeLong * stat.availableBlocksLong).toDouble() / (1024.0 * 1024.0 * 1024.0))
        val percent = ((occupiedStorageMemory / totalStorageMemory) * 100).toInt()
        return StorageMemoryModel(occupiedStorageMemory, totalStorageMemory, percent)
    }

    fun setScreenBrightness(value: Int) {
        try {
            Settings.System.putInt(
                AppClass.instance.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                value
            )
        } catch (e: Exception) {
        }
    }

}