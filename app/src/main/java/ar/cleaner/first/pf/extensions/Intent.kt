package ar.cleaner.first.pf.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

fun Context.usageAccessSettings(): Intent {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
        Intent(
            Settings.ACTION_USAGE_ACCESS_SETTINGS,
            Uri.parse(String.format("package:%s", packageName))
        )
    } else Intent(
        Settings.ACTION_USAGE_ACCESS_SETTINGS,
    )
}