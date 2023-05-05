package file_manager.start_screen.clean_checker

import file_manager.start_screen.gateways.LastCleanTime
import java.util.concurrent.TimeUnit

class CleanCheckerImpl(
    private val lastCleanTime: LastCleanTime
) : CleanChecker {

    override val isCleaned: Boolean
        get() {
            val lastCleanTime = lastCleanTime.provide()
            val currentTime = System.currentTimeMillis()
            val delta = currentTime - lastCleanTime
            return delta > TimeUnit.HOURS.toMillis(20)
        }
}