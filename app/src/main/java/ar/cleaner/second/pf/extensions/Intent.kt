package ar.cleaner.second.pf.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

fun usageAccessSettings(): Intent {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        Intent(
            Settings.ACTION_USAGE_ACCESS_SETTINGS
        )
    } else Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
    )
}

@SuppressLint("InlinedApi")
fun Context.manageExternal(): Intent =
    Intent().apply {
        action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
        addCategory("android.intent.category.DEFAULT")
        data = Uri.parse(String.format("package:%s", packageName))
    }