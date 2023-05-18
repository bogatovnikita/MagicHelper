package yin_kio.files_and_apps_manager.presentation.overview

import Yin_Koi.files_and_apps_manager.presentation.R
import android.content.Context

internal class Presenter(
    private val context: Context
) {

    fun presentButtonText() : String{
        return context.getString(R.string.file_apps_manager_delete)
    }

    fun presentButtonAlpha() : Float{
        return 0.5f
    }

}