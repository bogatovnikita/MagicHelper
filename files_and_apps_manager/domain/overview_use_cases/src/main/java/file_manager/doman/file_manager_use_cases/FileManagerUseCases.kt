package file_manager.doman.file_manager_use_cases

import file_manager.doman.file_manager_use_cases.ui_out.UiOuter
import file_manager.doman.file_manager_use_cases.ui_out.UpdateOut

class FileManagerUseCases(
    private val uiOuter: UiOuter
) {

    fun close(){
        uiOuter.close()
    }

    fun update(){
        uiOuter.out(UpdateOut())
    }

}