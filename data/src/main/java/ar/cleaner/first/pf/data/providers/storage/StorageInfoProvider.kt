package ar.cleaner.first.pf.data.providers.storage

import android.app.Application
import android.app.usage.StorageStatsManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.storage.StorageManager
import androidx.annotation.RequiresApi
import ar.cleaner.first.pf.domain.gateways.storage.Storage
import ar.cleaner.first.pf.domain.models.StorageInfo
import javax.inject.Inject

private const val GB = 1073741824.0

class StorageInfoProvider @Inject constructor(
    private val context: Application
): Storage {

    override fun getStorageInfo() : StorageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val storageStatsManager = storageStatsManager()
            val total = storageStatsManager.getTotalBytes(StorageManager.UUID_DEFAULT)
            val free = storageStatsManager.getFreeBytes(StorageManager.UUID_DEFAULT)
            val occupied = total - free

            StorageInfo(
                occupied = occupied.toGb(),
                total = total.toGb(),
                free = free.toGb(),
                usageMemoryPercents = ((occupied / total.toFloat() * 100)).toInt()
            )
        } else {
            val stats = statFs()
            val total = stats.totalBytes
            val free = stats.freeBytes
            val occupied = total - free
            StorageInfo(
                occupied = occupied.toGb(),
                total = total.toGb(),
                free = free.toGb(),
                usageMemoryPercents = ((occupied / total.toFloat() * 100)).toInt()
            )
        }
    }

    private fun statFs() = StatFs(Environment.getExternalStorageDirectory().absolutePath)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun storageStatsManager() =
        context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager

    private fun Long.toGb() = (this / GB)

}