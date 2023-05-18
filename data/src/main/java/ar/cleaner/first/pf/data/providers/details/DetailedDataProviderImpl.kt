package ar.cleaner.first.pf.data.providers.details

import android.app.ActivityManager
import android.content.Context
import ar.cleaner.first.pf.domain.models.details.BoostDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DetailedDataProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DetailedDataProvider {

    private val activityManager by lazy { context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
    private val memoryInfo by lazy { ActivityManager.MemoryInfo() }

    override fun getBoostDetails(): BoostDetails {
        return BoostDetails(
            totalRam = getTotalRam().convertToGb(),
            usedRam = getUsageRam().convertToGb(),
            usagePercents = getUsagePercent()
        )
    }

    private fun getTotalRam(): Long {
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem
    }

    private fun getUsageRam(): Long {
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem - memoryInfo.availMem
    }

    private fun getUsagePercent(): Int {
        activityManager.getMemoryInfo(memoryInfo)

        val totalMemoryBytes = memoryInfo.totalMem
        val usedMemoryBytes = (totalMemoryBytes - memoryInfo.availMem).toFloat()

        return ((usedMemoryBytes / totalMemoryBytes) * 100).toInt()
    }

    private fun Long.convertToGb(): Double = (this / (1000 * 1000 * 1000)).toDouble()
}