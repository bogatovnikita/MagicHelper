package ar.cleaner.first.pf.data.mapper

import android.app.ActivityManager
import android.app.usage.UsageStats
import android.content.pm.ApplicationInfo
import ar.cleaner.first.pf.domain.models.App
import ar.cleaner.first.pf.domain.models.BackgroundApp

fun ApplicationInfo.asApp(cacheSizeReadable: String, cacheSize: Long, name: String) = App(
    name = name,
    sizeReadable = cacheSizeReadable,
    packageName = packageName,
    size = (cacheSize).toDouble()
)

private fun String.toAppName(): String {
    return if (contains("com")) {
        substringAfterLast('.')
    } else this
}

fun UsageStats.asBackgroundApp(name: String) = BackgroundApp(
    packageName = packageName,
    name = name
)

fun ActivityManager.RunningAppProcessInfo.asBackgroundApp() = BackgroundApp(
    packageName = processName,
    name = processName.toAppName()
)
