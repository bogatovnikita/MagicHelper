package file_manager.start_screen.use_case

import file_manager.start_screen.clean_checker.CleanChecker
import file_manager.start_screen.gateways.UsedMem
import file_manager.start_screen.ui_out.UiOuter
import file_manager.start_screen.ui_out.UpdateOut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class StartScreenUseCaseImpl(
    private val uiOuter: UiOuter,
    private val usedMem: UsedMem,
    private val cleanChecker: CleanChecker,
    private val coroutineScope: CoroutineScope,
    private val dispatcher: CoroutineContext
) : StartScreenUseCase {


    override fun close(){
        uiOuter.close()
    }

    override fun scan(){
        uiOuter.showScanProgress()
    }

    override fun update(){
        coroutineScope.launch(dispatcher) {
            val usedMemOut = usedMem.provide()
            val isCleaned = cleanChecker.isCleaned

            val updateOut = UpdateOut(
                usedMemOut = usedMemOut,
                isCleaned = isCleaned
            )

            uiOuter.out(updateOut)
        }
    }

}