package file_manager.scan_progress.scan

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.SortingMode
import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.gateways.Ads
import file_manager.scan_progress.gateways.FilesAndApps
import file_manager.scan_progress.grouper.Grouper

internal class ScanActionImpl(
    private val uiOuter: UiOuter,
    private val delayer: Delayer,
    private val filesAndApps: FilesAndApps,
    private val fileManagerServer: FileManagerServer,
    private val grouper: Grouper,
    private val ads: Ads
): ScanAction {

    override suspend fun scan() {
        ads.preloadAd()
        val files = filesAndApps.provideFiles()
        val apps = filesAndApps.provideApps()

        uiOuter.showProgress()
        fileManagerServer.groups = grouper.groupFilesAndApps(
            files = files,
            apps = apps
        )
        fileManagerServer.setSortingMode(SortingMode.BigFirst)

        delayer.makeDelay()
        uiOuter.showInter()
    }
}