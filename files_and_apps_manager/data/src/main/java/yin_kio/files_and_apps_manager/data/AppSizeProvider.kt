@file:Suppress("DEPRECATION")
package yin_kio.files_and_apps_manager.data

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.pm.IPackageStatsObserver
import android.content.pm.PackageManager
import android.content.pm.PackageStats
import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.reflect.Method

class AppSizeProvider(
    private val context: Context
) {


    /**
     * Returns app size.
     * If error happened returns -1
     * */

    fun getAppSize(packageName: String) : Long{
        var size = -1L
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                size = getByStorageStatsManager(packageName)
            } else {
                getPackageSizeInfo().invoke(
                    context.packageManager,
                    packageName,
                    object : IPackageStatsObserver.Stub(){
                        override fun onGetStatsCompleted(
                            pStats: PackageStats?,
                            succeeded: Boolean
                        ) {
                            pStats?:return
                            size = pStats.cacheSize + pStats.dataSize
                        }
                    }
                )
            }
        } catch (ex: Exception){
            size = -1
        }


        return size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getByStorageStatsManager(packageName: String) : Long {
        val appInfo = getAppInfo(packageName)
        val storageStatsManager =
            context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
        val storage = storageStatsManager.queryStatsForUid(appInfo.storageUuid, appInfo.uid)
        return storage.cacheBytes + storage.dataBytes
    }

    private fun getAppInfo(packageName: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(0)
            )
        } else {
            context.packageManager.getApplicationInfo(packageName, 0)
        }

    private fun getPackageSizeInfo(): Method {
        return context.packageManager.javaClass.getMethod(
            "getPackageSizeInfo", String::class.java, IPackageStatsObserver::class.java
        )
    }

}