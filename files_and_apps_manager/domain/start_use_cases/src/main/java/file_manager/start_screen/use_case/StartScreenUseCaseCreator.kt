package file_manager.start_screen.use_case

import file_manager.start_screen.clean_checker.CleanCheckerImpl
import file_manager.start_screen.gateways.LastCleanTime
import file_manager.start_screen.gateways.UsedMem
import file_manager.start_screen.ui_out.UiOuter

object StartScreenUseCaseCreator {

    fun create(
        uiOuter: UiOuter,
        usedMem: UsedMem,
        lastCleanTime: LastCleanTime
    ) : StartScreenUseCase {

        return StartScreenUseCaseImpl(
            uiOuter = uiOuter,
            usedMem = usedMem,
            cleanChecker = CleanCheckerImpl(
                lastCleanTime = lastCleanTime
            )
        )

    }

}