package file_manager.scan_progress.scan

import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.gateways.Ads
import yin_kio.file_app_manager.updater.Updater
import yin_kio.file_app_manager.updater.UpdaterImpl

internal class ScanActionImpl(
    private val uiOuter: UiOuter,
    private val delayer: Delayer,
    private val updater: Updater,
    private val ads: Ads
): ScanAction {

    override suspend fun scan() {
        ads.preloadAd()
        uiOuter.showProgress()

        updater.update()

        delayer.makeDelay()
        uiOuter.showInter()
    }
}