package file_manager.scan_progress

import file_manager.scan_progress.gateways.Permissions
import file_manager.scan_progress.scan.ScanAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class ScanProgressUseCaseImpl(
    private val uiOuter: UiOuter,
    private val scanAction: ScanAction,
    private val permissions: Permissions,
    private val coroutineScope: CoroutineScope,
    private val dispatcher: CoroutineContext
) : ScanProgressUseCase {

    override fun close(){ coroutineScope.launch{ uiOuter.close() } }

    override fun requestPermission(){
        coroutineScope.launch { uiOuter.requestPermission() }
    }

    override fun scan(){
        coroutineScope.launch(dispatcher) {
            if (permissions.hasPermissions){
                scanAction.scan()
            } else {
                uiOuter.showPermissionDialog()
            }
        }

    }

}