package yin_kio.files_and_apps_manager.data

import android.app.ActivityManager
import android.content.Context
import file_manager.start_screen.UsedMemOut
import file_manager.start_screen.gateways.UsedMem

class UsedMemImpl(
    private val context: Context
) : UsedMem {

    override fun provide(): UsedMemOut {
        val memInfo = ActivityManager.MemoryInfo()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(memInfo)

        val occupied = memInfo.totalMem - memInfo.availMem
        val total = memInfo.totalMem

        return UsedMemOut(
            occupied = occupied,
            total = total
        )
    }
}