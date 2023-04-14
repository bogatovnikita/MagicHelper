package file_manager.doman.overview

import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.ItemSelectionOut
import file_manager.doman.overview.ui_out.UpdateOut

interface OutCreator {

    fun createAllSelectionOut() : AllSelectionOut
    fun createUpdateOut() : UpdateOut
    fun createItemSelectionOut(itemId: String) : ItemSelectionOut

}