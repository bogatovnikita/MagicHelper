package file_manager.doman.overview.use_case

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class OverviewUseCaseImpl(
    private val uiOuter: UiOuter,
    private val outCreator: OutCreator,
    private val server: FileManagerServer,
    private val deleteAction: DeleteAction,
    private val updateAction: UpdateAction,
    private val coroutineScope: CoroutineScope,
    private val dispatcher: CoroutineContext
) : OverviewUseCase {

    override fun close() = async{
        uiOuter.close()
    }

    override fun update() = async{
        updateAction.update()
    }

    override fun switchGroup(groupName: GroupName) = async{
        uiOuter.showGroup(groupName)
    }

    override fun switchAllSelection(groupName: GroupName) = async{
        server.switchAllSelection(groupName)
        uiOuter.out(outCreator.createAllSelectionOut())
    }

    override fun showSortingSelection() = async{
        uiOuter.showSortingSelection()
    }

    override fun switchItemSelection(groupName: GroupName, itemId: String) = async{
        server.switchItemSelection(groupName, itemId)
        uiOuter.out(outCreator.createItemSelectionOut(itemId))
    }

    override fun showDeleteDialog() = async{
        if (server.hasSelected){
            uiOuter.showDeleteDialog()
        }
    }

    override fun delete(groupName: GroupName) = async{
        deleteAction.deleteAndUpdate(groupName)
    }

    override fun hideDeleteDialog() = async{
        uiOuter.hideDeleteDialog()
    }

    override fun completeDelete() = async{
        uiOuter.hideDeleteDialog()
    }

    private fun async(
        action: suspend CoroutineScope.() -> Unit
    ){
        coroutineScope.launch(dispatcher, block = action)
    }

}