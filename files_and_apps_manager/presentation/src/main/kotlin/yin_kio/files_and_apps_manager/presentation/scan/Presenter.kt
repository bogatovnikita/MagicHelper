package yin_kio.files_and_apps_manager.presentation.scan

import android.content.Context
import com.example.permissions.hasStoragePermissions

internal class Presenter(
    private val context: Context
) {

    fun presentDialogPermissionCommand() : Command{
        if (!context.hasStoragePermissions()) return Command.ShowStoragePermissionDialog
        return Command.ShowUsageStatsPermissionDialog
    }

    fun presentRequestPermissionCommand() : Command{
        if (!context.hasStoragePermissions()) return Command.RequestStoragePermission
        return Command.RequestUsageStatsPermission
    }

}