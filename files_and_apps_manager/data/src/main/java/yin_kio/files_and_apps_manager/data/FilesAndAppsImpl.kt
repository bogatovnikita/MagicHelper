package yin_kio.files_and_apps_manager.data

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import file_manager.domain.server.FileOrApp
import yin_kio.file_app_manager.updater.FilesAndApps


class FilesAndAppsImpl(
    private val context: Context
) : FilesAndApps {

    private val appSizeProvider = AppSizeProvider(context)

    override suspend fun provideFiles(): List<FileOrApp> {
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

    override suspend fun provideApps(): List<FileOrApp> {
        return getAllPackages().filter { !isSystemPackage(it.applicationInfo) }
            .map {
                val packageName = it.packageName
                FileOrApp(
                    id = it.packageName,
                    size = appSizeProvider.getAppSize(packageName),
                    lastTimeUsed = getUsageStats()[packageName]?.lastTimeUsed?:0L,
                    type = FileOrApp.Type.App
                )
            }
    }


    private fun getAllPackages() : List<PackageInfo>{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(0))
        } else {
            context.packageManager.getInstalledPackages(0)
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

    private fun isSystemPackage(applicationInfo: ApplicationInfo): Boolean {
        return applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
}