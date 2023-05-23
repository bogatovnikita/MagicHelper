package file_manager.doman.overview.use_case

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.FilesDeleter
import file_manager.doman.overview.ui_out.UiOuter
import yin_kio.file_app_manager.updater.Updater

internal class DeleteActionImpl(
    private val filesDeleter: FilesDeleter,
    private val server: FileManagerServer,
    private val updateUIAction: UpdateUIAction,
    private val updater: Updater,
    private val uiOuter: UiOuter,
    private val deleteTimeSaver: DeleteTimeSaver
) : DeleteAction {

    override suspend fun deleteAndUpdate(groupName: GroupName) {
        uiOuter.showDeleteProgress()

        filesDeleter.delete(server.getSelected(groupName).map { it.id })
        server.clearSelected()
        deleteTimeSaver.saveDeleteTime()
        updater.update()
        updateUIAction.update()

        uiOuter.showDeleteCompletion()

    }
}