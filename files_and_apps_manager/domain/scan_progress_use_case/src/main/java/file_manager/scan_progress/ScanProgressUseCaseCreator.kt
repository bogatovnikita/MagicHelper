package file_manager.scan_progress

import file_manager.domain.server.FileManagerServer
import file_manager.scan_progress.gateways.Ads
import file_manager.scan_progress.gateways.FilesAndApps
import file_manager.scan_progress.gateways.Permissions
import file_manager.scan_progress.grouper.GrouperImpl
import file_manager.scan_progress.scan.Delayer
import file_manager.scan_progress.scan.ScanActionImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object ScanProgressUseCaseCreator {

    fun create(
        uiOuter: UiOuter,
        filesAndApps: FilesAndApps,
        fileManagerServer: FileManagerServer,
        permissions: Permissions,
        ads: Ads,
        coroutineScope: CoroutineScope
    ) : ScanProgressUseCase{
        return ScanProgressUseCaseImpl(
            uiOuter = uiOuter,
            scanAction = ScanActionImpl(
                uiOuter = uiOuter,
                delayer = Delayer(),
                filesAndApps = filesAndApps,
                fileManagerServer = fileManagerServer,
                grouper = GrouperImpl(),
                ads = ads
            ),
            permissions = permissions,
            coroutineScope = coroutineScope,
            dispatcher = Dispatchers.IO
        )
    }

}