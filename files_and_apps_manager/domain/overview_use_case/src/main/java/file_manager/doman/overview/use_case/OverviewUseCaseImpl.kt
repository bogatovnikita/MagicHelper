package file_manager.doman.overview.use_case

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.domain.server.SortingMode
import file_manager.doman.overview.ui_out.OutCreator
import file_manager.doman.overview.ui_out.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import yin_kio.file_app_manager.updater.ContentUpdater
import kotlin.coroutines.CoroutineContext

internal class OverviewUseCaseImpl(
    private val uiOuter: UiOuter,
    private val outCreator: OutCreator,
    private val server: FileManagerServer,
    private val deleteAction: DeleteAction,
    private val uiUpdater: UpdateUIAction,
    private val coroutineScope: CoroutineScope,
    private val contentUpdater: ContentUpdater,
    private val dispatcher: CoroutineContext
) : OverviewUseCase {

    override fun close() = async{
        uiOuter.close()
    }

    override fun update() = async{
        uiUpdater.update()
    }

    override fun switchGroup(groupName: GroupName) = async{
        server.selectedGroup = groupName
        uiOuter.out(outCreator.createGroupSwitchingOut())
    }

    override fun switchAllSelection(groupName: GroupName) = async{
        server.switchAllSelection(groupName)
        uiOuter.out(outCreator.createAllSelectionOut())
    }

    override fun showSortingSelection() = async{
        uiOuter.showSortingSelection()
    }

    override fun hideSortingSelection() = async {
        uiOuter.hideSortingSelection()
    }

    override fun switchItemSelection(itemId: String, selectable: Selectable) {
        server.switchItemSelection(itemId)
        uiOuter.out(outCreator.createItemSelectionOut(itemId))
        selectable.setSelected(server.isItemSelected(itemId))
    }

    override fun showAskDeleteDialog() = async{
        if (server.hasSelected){
            uiOuter.showDeleteDialog()
        }
    }

    override fun delete() = async{
        deleteAction.deleteAndUpdate(server.selectedGroup)
    }

    override fun hideAskDeleteDialog() = async{
        uiOuter.hideAskDeleteDialog()
    }

    override fun completeDelete() = async{
        if (server.selectedGroup == GroupName.Apps){
            uiOuter.showUpdateAppsProgress()
            contentUpdater.updateApps()
            uiUpdater.update()
            uiOuter.hideUpdateAppsProgress()
        } else {
            uiOuter.hideDoneDialog()
        }
    }



    override fun setSortingMode(sortingMode: SortingMode) = async {
        server.setSortingMode(sortingMode)
        uiOuter.out(outCreator.createSortingModeOut())
    }

    override fun updateSelectable(itemId: String, selectable: Selectable) {
        val isSelected = server.isItemSelected(itemId)
        selectable.setSelected(isSelected)
    }

    private fun async(
        action: suspend CoroutineScope.() -> Unit
    ){
        coroutineScope.launch(dispatcher, block = action)
    }
}