package file_manager.doman.overview.use_cases

import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.OutCreator
import file_manager.doman.overview.ui_out.*

class OverviewUseCases(
    private val uiOuter: UiOuter,
    private val outCreator: OutCreator,
    private val server: FileManagerServer,
    private val deleteUseCase: DeleteUseCase
) {

    fun close(){
        uiOuter.close()
    }

    fun update(){
        uiOuter.out(outCreator.createUpdateOut())
    }

    fun switchGroup(groupName: GroupName){
        uiOuter.showGroup(groupName)
    }

    fun switchAllSelection(){
        server.switchAllSelection()
        uiOuter.out(outCreator.createAllSelectionOut())
    }

    fun showSortingSelection(){
        uiOuter.showSortingSelection()
    }

    fun switchItemSelection(itemId: String){
        server.switchItemSelection(itemId)
        uiOuter.out(outCreator.createItemSelectionOut(itemId))
    }

    fun showDeleteDialog(){
        if (server.hasSelected){
            uiOuter.showDeleteDialog()
        }
    }

    fun delete(){
        deleteUseCase.delete()
    }

}