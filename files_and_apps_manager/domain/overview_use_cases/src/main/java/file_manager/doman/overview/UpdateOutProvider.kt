package file_manager.doman.overview

import file_manager.doman.overview.ui_out.UpdateOut

interface UpdateOutProvider {

    fun provide() : UpdateOut

}