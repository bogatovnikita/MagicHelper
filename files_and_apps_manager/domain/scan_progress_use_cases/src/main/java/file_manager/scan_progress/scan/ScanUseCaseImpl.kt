package file_manager.scan_progress.scan

import file_manager.domain.server.FileManagerServer
import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.gateways.FilesAndApps

class ScanUseCaseImpl(
    private val uiOuter: UiOuter,
    private val delayer: Delayer,
    private val filesAndApps: FilesAndApps,
    private val fileManagerServer: FileManagerServer
): ScanUseCase {

    override suspend fun scan() {
        val files = filesAndApps.provide()
        uiOuter.showProgress()
        fileManagerServer.setFilesAndApps(files)
        delayer.makeDelay()
        uiOuter.showInter()
    }
}