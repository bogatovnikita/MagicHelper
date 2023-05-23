package file_manager.doman.overview

import file_manager.domain.server.FileManagerServer
import file_manager.doman.overview.gateways.AppsDeleter
import file_manager.doman.overview.gateways.DeleteTimeSaver
import file_manager.doman.overview.gateways.FilesDeleter
import file_manager.doman.overview.ui_out.OutCreatorImpl
import file_manager.doman.overview.ui_out.UiOuter
import file_manager.doman.overview.use_case.DeleteActionImpl
import file_manager.doman.overview.use_case.OverviewUseCase
import file_manager.doman.overview.use_case.OverviewUseCaseImpl
import file_manager.doman.overview.use_case.UpdateUIActionImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import yin_kio.file_app_manager.updater.ContentUpdater

object OverviewUseCaseCreator {

    fun create(
        uiOuter: UiOuter,
        server: FileManagerServer,
        filesDeleter: FilesDeleter,
        deleteTimeSaver: DeleteTimeSaver,
        contentUpdater: ContentUpdater,
        appsDeleter: AppsDeleter,
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
            filesDeleter = filesDeleter,
            server = server,
            updateUIAction = updateAction,
            uiOuter = uiOuter,
            deleteTimeSaver = deleteTimeSaver,
            contentUpdater = contentUpdater,
            appsDeleter = appsDeleter
        )

        return OverviewUseCaseImpl(
            uiOuter = uiOuter,
            outCreator = outCreator,
            server = server,
            deleteAction = deleteAction,
            uiUpdater = updateAction,
            contentUpdater = contentUpdater,
            coroutineScope = coroutineScope,
            dispatcher = Dispatchers.IO
        )

    }

}