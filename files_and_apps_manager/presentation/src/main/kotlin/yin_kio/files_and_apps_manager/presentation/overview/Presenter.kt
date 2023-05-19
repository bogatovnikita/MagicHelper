package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import android.content.Context
import file_manager.domain.server.SortingMode

internal class Presenter(
    private val context: Context
) {

    fun presentButtonText() : String{
        return context.getString(R.string.file_apps_manager_delete)
    }

    fun presentButtonAlpha() : Float{
        return 0.5f
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

}