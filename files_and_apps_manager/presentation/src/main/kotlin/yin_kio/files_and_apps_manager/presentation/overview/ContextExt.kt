package yin_kio.files_and_apps_manager.presentation.overview

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import java.lang.Exception


fun Context.getAppInfoOrNull(packageName: String): ApplicationInfo?{
    return try {
        getAppInfo(packageName)
    } catch (ex: Exception){
        null
    }
}

fun Context.getAppInfo(packageName: String) : ApplicationInfo {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(0))
    } else {
        packageManager.getApplicationInfo(packageName, 0)
    }
}