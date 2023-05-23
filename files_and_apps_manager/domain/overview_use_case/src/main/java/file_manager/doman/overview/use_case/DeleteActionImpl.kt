package file_manager.doman.overview.use_case

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.gateways.AppsDeleter
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.FilesDeleter
import file_manager.doman.overview.ui_out.UiOuter
import yin_kio.file_app_manager.updater.ContentUpdater

internal class DeleteActionImpl(
    private val filesDeleter: FilesDeleter,
    private val server: FileManagerServer,
    private val updateUIAction: UpdateUIAction,
    private val contentUpdater: ContentUpdater,
    private val uiOuter: UiOuter,
    private val deleteTimeSaver: DeleteTimeSaver,
    private val appsDeleter: AppsDeleter
) : DeleteAction {

    override suspend fun deleteAndUpdate(groupName: GroupName) {
        uiOuter.showDeleteProgress()

        val selected = server.getSelected(groupName).map { it.id }

        if (server.selectedGroup == GroupName.Apps){
            appsDeleter.deleteApps(selected)
        } else {
            filesDeleter.delete(selected)
            contentUpdater.updateFilesAndApps()
            updateUIAction.update()
        }

        deleteTimeSaver.saveDeleteTime()
        uiOuter.showDeleteCompletion()

    }
}