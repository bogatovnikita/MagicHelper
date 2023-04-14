package file_manager.doman.overview

import file_manager.doman.overview.ui_out.GroupName
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.ui_out.UpdateOut

class OverviewUseCases(
    private val uiOuter: UiOuter,
    private val updateOutProvider: UpdateOutProvider
) {

    fun close(){
        uiOuter.close()
    }

    fun update(){
        uiOuter.out(updateOutProvider.provide())
    }

    fun switchGroup(groupName: GroupName){
        uiOuter.showGroup(groupName)
    }

}