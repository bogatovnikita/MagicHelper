package file_manager.doman.overview.use_case

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.Deleter
import file_manager.doman.overview.ui_out.UiOuter

internal class DeleteActionImpl(
    private val deleter: Deleter,
    private val server: FileManagerServer,
    private val updateAction: UpdateAction,
    private val uiOuter: UiOuter,
    private val deleteTimeSaver: DeleteTimeSaver
) : DeleteAction {

    override suspend fun deleteAndUpdate(groupName: GroupName) {
        uiOuter.showDeleteProgress()

        deleter.delete(server.getSelected(groupName).map { it.id })
        deleteTimeSaver.saveDeleteTime()
        updateAction.update()

        uiOuter.showDeleteCompletion()

    }
}