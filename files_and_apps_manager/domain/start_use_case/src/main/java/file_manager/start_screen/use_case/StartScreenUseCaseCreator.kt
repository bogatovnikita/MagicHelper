package file_manager.start_screen.use_case

import yin_kio.clean_checker.LastCleanTime
import file_manager.start_screen.gateways.UsedMem
import file_manager.start_screen.ui_out.UiOuter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import yin_kio.clean_checker.CleanCheckerImpl

object StartScreenUseCaseCreator {

    fun create(
        uiOuter: UiOuter,
        usedMem: UsedMem,
        lastCleanTime: LastCleanTime,
        coroutineScope: CoroutineScope
    ) : StartScreenUseCase {

        return StartScreenUseCaseImpl(
            uiOuter = uiOuter,
            usedMem = usedMem,
            cleanChecker = CleanCheckerImpl(
                lastCleanTime = lastCleanTime
            ),
            coroutineScope = coroutineScope,
            dispatcher = Dispatchers.IO
        )

    }

}