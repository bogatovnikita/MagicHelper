package file_manager.scan_progress.facade

import file_manager.scan_progress.UiOuter
import file_manager.scan_progress.scan.ScanUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ScanProgressUseCases(
    private val uiOuter: UiOuter,
    private val scanUseCase: ScanUseCase,
    private val permissions: Permissions,
    private val coroutineScope: CoroutineScope,
    private val dispatcher: CoroutineContext
) {

    fun close(){ uiOuter.close() }

    fun requestPermission(){
        uiOuter.requestPermission()
    }

    fun scan(){
        coroutineScope.launch(dispatcher) {
            if (permissions.hasPermissions){
                scanUseCase.scan()
            } else {
                uiOuter.showPermissionDialog()
            }
        }

    }

}