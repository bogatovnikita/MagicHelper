package yin_kio.files_and_apps_manager.data

import android.Manifest
import android.content.Context
import com.example.permissions.hasSpecial
import com.example.permissions.hasStoragePermissions
import file_manager.scan_progress.gateways.Permissions

class PermissionsImpl(
    private val context: Context
) : Permissions {

    override val hasStoragePermission: Boolean
        get() = context.hasStoragePermissions() && context.hasSpecial(Manifest.permission.PACKAGE_USAGE_STATS)
}