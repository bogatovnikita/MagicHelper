package file_manager.scan_progress.scan

import file_manager.domain.server.FileManagerServer
import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.gateways.FilesAndApps
import file_manager.scan_progress.grouper.Grouper

internal class ScanActionImpl(
    private val uiOuter: UiOuter,
    private val delayer: Delayer,
    private val filesAndApps: FilesAndApps,
    private val fileManagerServer: FileManagerServer,
    private val grouper: Grouper
): ScanAction {

    override suspend fun scan() {
        val files = filesAndApps.provideFiles()
        val apps = filesAndApps.provideApps()

        uiOuter.showProgress()
        fileManagerServer.groups = grouper.groupFilesAndApps(
            files = files,
            apps = apps
        )
        delayer.makeDelay()
        uiOuter.showInter()
    }
}