package file_manager.scan_progress

import file_manager.scan_progress.gateways.Ads
import file_manager.scan_progress.gateways.Permissions
import file_manager.scan_progress.scan.Delayer
import file_manager.scan_progress.scan.ScanActionImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import yin_kio.file_app_manager.updater.UpdaterImpl

object ScanProgressUseCaseCreator {

    fun create(
        uiOuter: UiOuter,
        updater: UpdaterImpl,
        permissions: Permissions,
        ads: Ads,
        coroutineScope: CoroutineScope
    ) : ScanProgressUseCase{
        return ScanProgressUseCaseImpl(
            uiOuter = uiOuter,
            scanAction = ScanActionImpl(
                uiOuter = uiOuter,
                delayer = Delayer(),
                updater = updater,
                ads = ads
            ),
            permissions = permissions,
            coroutineScope = coroutineScope,
            dispatcher = Dispatchers.IO
        )
    }

}