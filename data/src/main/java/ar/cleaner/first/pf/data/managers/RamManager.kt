package ar.cleaner.first.pf.data.managers

import android.app.ActivityManager
import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import ar.cleaner.first.pf.data.extensions.activityManager
import ar.cleaner.first.pf.data.extensions.getAppName
import ar.cleaner.first.pf.data.extensions.isSystem
import ar.cleaner.first.pf.data.extensions.usageStatsManager
import ar.cleaner.first.pf.data.mapper.asBackgroundApp
import ar.cleaner.first.pf.domain.const.Const
import ar.cleaner.first.pf.domain.models.BackgroundApp
import ar.cleaner.first.pf.domain.utils.lazyStateFlow
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.roundToInt

class RamManager @Inject constructor(
    private val context: Application,
    private val defaultScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) {

    fun getAvailableRam(scope: CoroutineScope? = null) = lazyStateFlow(
        scope = defaultScope,
        defaultValue = Const.DEFAULT_DOUBLE_VALUE
    ) {
        while (this.isActive) {
            val actManager = context.activityManager
            val memInfo = ActivityManager.MemoryInfo()
            actManager.getMemoryInfo(memInfo)
            val mem = abs(memInfo.availMem.toDouble() / MEGABYTE)
            trySend(mem)
            delay(3000)
        }
    }

    val totalRam: Int by lazy {
        val actManager = context.activityManager

        val memInfo = ActivityManager.MemoryInfo()

        actManager.getMemoryInfo(memInfo)
        (memInfo.totalMem.toDouble() / (MEGABYTE)).roundToInt()
    }

    suspend fun getBackgroundApps(): List<BackgroundApp> = withContext(dispatcher) {
        val res = mutableListOf<BackgroundApp>()
        val currentTime = System.currentTimeMillis()
        context.usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            currentTime - Const.DAY_MILLIS,
            currentTime
        ).forEach {
            try {
                if (context.isSystem(it.packageName)) res.add(
                    it.asBackgroundApp(
                        name = context.getAppName(it.packageName)
                    )
                )
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        res
    }

    private companion object {
        private const val MEGABYTE = 1024 * 1024 * 1024
    }
}