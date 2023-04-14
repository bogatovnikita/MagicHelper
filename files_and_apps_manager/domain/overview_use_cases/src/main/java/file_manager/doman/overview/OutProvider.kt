package file_manager.doman.overview

import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.UpdateOut

interface OutProvider {

    fun createAllSelectionOut() : AllSelectionOut
    fun createUpdateOut() : UpdateOut

}