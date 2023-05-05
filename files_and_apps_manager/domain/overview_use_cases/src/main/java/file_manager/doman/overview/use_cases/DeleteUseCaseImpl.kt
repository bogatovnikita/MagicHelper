package file_manager.doman.overview.use_cases

import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.Deleter
import file_manager.doman.overview.ui_out.UiOuter

internal class DeleteUseCaseImpl(
    private val deleter: Deleter,
    private val server: FileManagerServer,
    private val updateUseCase: UpdateUseCase,
    private val uiOuter: UiOuter,
    private val deleteTimeSaver: DeleteTimeSaver
) : DeleteUseCase {

    override fun deleteAndUpdate() {
        uiOuter.showDeleteProgress()

        deleter.delete(server.selected)
        deleteTimeSaver.saveDeleteTime()
        updateUseCase.update()

        uiOuter.showDeleteCompletion()

    }
}