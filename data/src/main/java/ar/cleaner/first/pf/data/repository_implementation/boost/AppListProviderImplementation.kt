package ar.cleaner.first.pf.data.repository_implementation.boost

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import ar.cleaner.first.pf.data.providers.apps.AppsProvider
import ar.cleaner.first.pf.domain.models.RunningApp
import ar.cleaner.first.pf.domain.repositorys.boosting.AppListProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppListProviderImplementation @Inject constructor(
    private val appsProvider: AppsProvider,
    @ApplicationContext private val context: Context
) : AppListProvider {

    override suspend fun getRunningApp(): List<RunningApp> {
        return appsProvider.getInstalledApp()
            .map { getApp(it) }
            .filter {
                it.packageName.isNotEmpty()
                        && it.packageName != context.packageName
                        && context.packageManager.getLaunchIntentForPackage(it.packageName) != null
                        && !it.packageName.contains(".test")
            }
    }

    private fun getApp(packageInfo: PackageInfo): RunningApp {
        return try {
            RunningApp(
                nameApp = getName(packageInfo.packageName),
                iconApp = getIcon(packageInfo.packageName),
                packageName = packageInfo.packageName
            )
        } catch (e: java.lang.Exception) {
            RunningApp()
        }
    }

    private fun getName(packageName: String): String {
        return getPackageInfo(packageName).applicationInfo.loadLabel(context.packageManager)
            .toString()
    }

    private fun getIcon(packageName: String): Int {
        return getPackageInfo(packageName).applicationInfo.icon
    }

    private fun getPackageInfo(packageName: String): PackageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(
                packageName,
                PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            @Suppress("DEPRECATION") context.packageManager.getPackageInfo(packageName, 0)
        }
    }
}