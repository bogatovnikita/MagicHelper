package yin_kio.files_and_apps_manager.data

import android.app.usage.StorageStatsManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.storage.StorageManager
import androidx.annotation.RequiresApi
import file_manager.start_screen.gateways.UsedMem
import file_manager.start_screen.ui_out.UsedMemOut

class UsedMemImpl(
    private val context: Context
) : UsedMem {

    override fun provide(): UsedMemOut {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val storageStatsManager = storageStatsManager()
            val total = storageStatsManager.getTotalBytes(StorageManager.UUID_DEFAULT)
            val free = storageStatsManager.getFreeBytes(StorageManager.UUID_DEFAULT)
            val occupied = total - free
            UsedMemOut(occupied = occupied, total = total)
        } else {
            val stats = statFs()
            val total = stats.totalBytes
            val free = stats.freeBytes
            val occupied = total - free
            UsedMemOut(occupied = occupied, total = total)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun storageStatsManager() =
        context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager

    private fun statFs() = StatFs(Environment.getExternalStorageDirectory().absolutePath)

}