package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import android.content.Context
import android.text.format.Formatter.formatFileSize
import file_manager.domain.server.FileOrApp
import file_manager.domain.server.SortingMode
import yin_kio.files_and_apps_manager.presentation.overview.models.FileOrAppItem
import kotlin.io.path.Path
import kotlin.io.path.name

internal class Presenter(
    private val context: Context
) {

    fun presentButtonText(selectionCount: Int) : String{
        return if (selectionCount <= 0){
            context.getString(R.string.file_apps_manager_delete)
        } else {
            context.getString(R.string.file_apps_manager_delete_D, selectionCount)
        }
    }

    fun presentButtonAlpha(selectionCount: Int) : Float{
        return if (selectionCount <= 0){
            0.5f
        } else{
            1f
        }
    }

    fun presentSortingMode(sortingMode: SortingMode) : String{
        val strId = when(sortingMode){
            SortingMode.NewFirst -> R.string.file_apps_manager_sort_new_first
            SortingMode.OldFirst -> R.string.file_apps_manager_sort_old_first
            SortingMode.BigFirst -> R.string.file_apps_manager_sort_big_first
            SortingMode.SmallFirst -> R.string.file_apps_manager_sort_small_first
        }
        return context.getString(strId)
    }

    fun presentFilesOrApps(fileOrApps: List<FileOrApp>) : List<FileOrAppItem>{
        return fileOrApps.map {
            FileOrAppItem(
                id = it.id,
                name = presentName(it),
                description = presentDescription(it),
            )
        }
    }

    private fun presentName(fileOrApp: FileOrApp) : String {
        return when(fileOrApp.type){
            FileOrApp.Type.File -> Path(fileOrApp.id).name
            FileOrApp.Type.App -> context.getAppInfoOrNull(fileOrApp.id)?.loadLabel(context.packageManager).toString()
        }
    }

    private fun presentDescription(fileOrApp: FileOrApp) : String{
        if (fileOrApp.type == FileOrApp.Type.App) return ""

        return "${formatFileSize(context, fileOrApp.size)} • ${fileOrApp.id}"
    }

}