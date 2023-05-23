package file_manager.scan_progress.scan

import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.gateways.Ads
import yin_kio.file_app_manager.updater.ContentUpdater

internal class ScanActionImpl(
    private val uiOuter: UiOuter,
    private val delayer: Delayer,
    private val contentUpdater: ContentUpdater,
    private val ads: Ads
): ScanAction {

    override suspend fun scan() {
        ads.preloadAd()
        uiOuter.showProgress()

        contentUpdater.updateFilesAndApps()

        delayer.makeDelay()
        uiOuter.showInter()
    }
}