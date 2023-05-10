package yin_kio.files_and_apps_manager.data

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Environment
import file_manager.scan_progress.gateways.FilesAndApps


class FilesAndAppsImpl(
    private val context: Context
) : FilesAndApps {

    override fun provideFiles(): List<String> {
         return Environment.getExternalStorageDirectory()
            .walkTopDown()
            .filter { it.isFile }
            .map { it.absolutePath }
            .toList()
    }

    override fun provideApps(): List<String> {
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
            .map { it.activityInfo.packageName }
    }

    private fun isSystemPackage(ri: ResolveInfo): Boolean {
        return ri.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
}