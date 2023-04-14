package file_manager.doman.overview

import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.ui_out.AllSelectionOut
import file_manager.doman.overview.ui_out.GroupName
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut

class OverviewUseCases(
    private val uiOuter: UiOuter,
    private val outProvider: OutProvider,
    private val server: FileManagerServer
) {

    fun close(){
        uiOuter.close()
    }

    fun update(){
        uiOuter.out(outProvider.createUpdateOut())
    }

    fun switchGroup(groupName: GroupName){
        uiOuter.showGroup(groupName)
    }

    fun switchAllSelection(){
        server.switchAllSelection()
        uiOuter.out(outProvider.createAllSelectionOut())
    }

}