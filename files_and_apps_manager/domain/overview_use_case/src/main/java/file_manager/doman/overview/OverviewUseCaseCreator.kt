package file_manager.doman.overview

import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.Deleter
import file_manager.doman.overview.ui_out.OutCreatorImpl
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_case.DeleteActionImpl
import file_manager.doman.overview.use_case.OverviewUseCase
import file_manager.doman.overview.use_case.OverviewUseCaseImpl
import file_manager.doman.overview.use_case.UpdateActionImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object OverviewUseCaseCreator {

    fun create(
        uiOuter: UiOuter,
        server: FileManagerServer,
        deleter: Deleter,
        deleteTimeSaver: DeleteTimeSaver,
        coroutineScope: CoroutineScope
    ) : OverviewUseCase{

        val outCreator = OutCreatorImpl(
            server = server
        )

        val updateAction = UpdateActionImpl(
            uiOuter = uiOuter,
            outCreator = outCreator
        )

        val deleteAction = DeleteActionImpl(
            deleter = deleter,
            server = server,
            updateAction = updateAction,
            uiOuter = uiOuter,
            deleteTimeSaver = deleteTimeSaver,
        )

        return OverviewUseCaseImpl(
            uiOuter = uiOuter,
            outCreator = outCreator,
            server = server,
            deleteAction = deleteAction,
            updateAction = updateAction,
            coroutineScope = coroutineScope,
            dispatcher = Dispatchers.IO
        )

    }

}