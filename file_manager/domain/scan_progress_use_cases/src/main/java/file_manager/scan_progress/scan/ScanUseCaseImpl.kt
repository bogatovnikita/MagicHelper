package file_manager.scan_progress.scan

import file_manager.domain.server.FileManagerServer
import file_manager.scan_progress.UiOuter

class ScanUseCaseImpl(
    private val uiOuter: UiOuter,
    private val delayer: Delayer,
    private val files: Files,
    private val fileManagerServer: FileManagerServer
): ScanUseCase {

    override suspend fun scan() {
        val files = files.provide()
        uiOuter.showProgress()
        fileManagerServer.setFiles(files)
        delayer.makeDelay()
        uiOuter.showInter()
    }
}