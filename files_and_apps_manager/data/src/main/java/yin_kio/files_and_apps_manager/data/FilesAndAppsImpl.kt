package yin_kio.files_and_apps_manager.data

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Environment
import file_manager.domain.server.FileOrApp
import file_manager.scan_progress.gateways.FilesAndApps


class FilesAndAppsImpl(
    private val context: Context
) : FilesAndApps {

    private val appSizeProvider = AppSizeProvider(context)

    override fun provideFiles(): List<FileOrApp> {
        return Environment.getExternalStorageDirectory()
            .walkTopDown()
            .filter { it.isFile }
            .map {
                FileOrApp(
                    id = it.absolutePath,
                    size = it.length(),
                    lastTimeUsed = it.lastModified(),
                    type = FileOrApp.Type.File
                )
            }
            .toList()
    }

    override fun provideApps(): List<FileOrApp> {
        val resolveInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.queryIntentActivities(
                Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
                PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
            )
        } else {
            context.packageManager.queryIntentActivities(
                Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
                PackageManager.GET_META_DATA
            )
        }



        return resolveInfos.filter { !isSystemPackage(it) }
            .map {
                val packageName = it.activityInfo.packageName
                FileOrApp(
                    id = it.activityInfo.packageName,
                    size = appSizeProvider.getAppSize(packageName),
                    lastTimeUsed = getUsageStats()[packageName]?.lastTimeUsed?:0L,
                    type = FileOrApp.Type.App
                )
            }
    }

    private fun getUsageStats(): Map<String, UsageStats> {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_BEST,
            0,
            System.currentTimeMillis()
        ).associateBy { it.packageName }
    }

    private fun isSystemPackage(ri: ResolveInfo): Boolean {
        return ri.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
}