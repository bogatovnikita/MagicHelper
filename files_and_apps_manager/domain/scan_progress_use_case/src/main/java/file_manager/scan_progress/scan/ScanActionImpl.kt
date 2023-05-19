package file_manager.scan_progress.scan

import file_manager.domain.server.FileManagerServer
import file_manager.domain.server.GroupName
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

        println("!!! files: ${files.size}, apps: ${apps.size}")

        uiOuter.showProgress()
        fileManagerServer.groups = grouper.groupFilesAndApps(
            files = files,
            apps = apps
        )

        println("!!! groups: ${fileManagerServer.groups[GroupName.Apps]!!.content}")
        delayer.makeDelay()
        uiOuter.showInter()
    }
}