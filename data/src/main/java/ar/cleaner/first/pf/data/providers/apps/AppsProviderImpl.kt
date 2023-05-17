package ar.cleaner.first.pf.data.providers.apps

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppsProviderImpl @Inject constructor(@ApplicationContext private val context: Context) :
    AppsProvider {

    override suspend fun getInstalledApp(): List<PackageInfo> {
        return context.packageManager.getInstalledPackages(0).filter { packageInfo ->
            packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP == 0 &&
                    packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0 &&
                    !packageInfo.packageName.contains(".test")
        }
    }

    override suspend fun getSystemApp(): List<PackageInfo> {
        return context.packageManager.getInstalledPackages(0).filter {
            it.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0 &&
                    it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0 &&
                    !it.packageName.contains(".test")
        }
    }
}