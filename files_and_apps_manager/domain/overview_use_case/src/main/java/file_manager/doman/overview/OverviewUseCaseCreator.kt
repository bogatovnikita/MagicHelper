package file_manager.doman.overview

import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.Deleter
import file_manager.doman.overview.ui_out.OutCreatorImpl
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_case.DeleteActionImpl
import file_manager.doman.overview.use_case.OverviewUseCase
import file_manager.doman.overview.use_case.OverviewUseCaseImpl
import file_manager.doman.overview.use_case.UpdateUIActionImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import yin_kio.file_app_manager.updater.Updater

object OverviewUseCaseCreator {

    fun create(
        uiOuter: UiOuter,
        server: FileManagerServer,
        deleter: Deleter,
        deleteTimeSaver: DeleteTimeSaver,
        updater: Updater,
        coroutineScope: CoroutineScope
    ) : OverviewUseCase{

        val outCreator = OutCreatorImpl(
            server = server
        )

        val updateAction = UpdateUIActionImpl(
            uiOuter = uiOuter,
            outCreator = outCreator,
        )

        val deleteAction = DeleteActionImpl(
            deleter = deleter,
            server = server,
            updateUIAction = updateAction,
            uiOuter = uiOuter,
            deleteTimeSaver = deleteTimeSaver,
            updater = updater
        )

        return OverviewUseCaseImpl(
            uiOuter = uiOuter,
            outCreator = outCreator,
            server = server,
            deleteAction = deleteAction,
            updateUIAction = updateAction,
            coroutineScope = coroutineScope,
            dispatcher = Dispatchers.IO
        )

    }

}