package file_manager.doman.overview.use_case

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.*

internal class OverviewUseCase(
    private val uiOuter: UiOuter,
    private val outCreator: OutCreator,
    private val server: FileManagerServer,
    private val deleteAction: DeleteAction,
    private val updateAction: UpdateAction
) {

    fun close(){
        uiOuter.close()
    }

    fun update(){
        updateAction.update()
    }

    fun switchGroup(groupName: GroupName){
        uiOuter.showGroup(groupName)
    }

    fun switchAllSelection(groupName: GroupName){
        server.switchAllSelection(groupName)
        uiOuter.out(outCreator.createAllSelectionOut())
    }

    fun showSortingSelection(){
        uiOuter.showSortingSelection()
    }

    fun switchItemSelection(groupName: GroupName, itemId: String){
        server.switchItemSelection(groupName, itemId)
        uiOuter.out(outCreator.createItemSelectionOut(itemId))
    }

    fun showDeleteDialog(){
        if (server.hasSelected){
            uiOuter.showDeleteDialog()
        }
    }

    fun delete(groupName: GroupName){
        deleteAction.deleteAndUpdate(groupName)
    }

    fun hideDeleteDialog(){
        uiOuter.hideDeleteDialog()
    }

    fun completeDelete(){
        uiOuter.hideDeleteDialog()
    }

}