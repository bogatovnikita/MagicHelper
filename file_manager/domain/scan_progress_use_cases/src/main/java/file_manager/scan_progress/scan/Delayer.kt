package file_manager.scan_progress.scan

import kotlinx.coroutines.delay

class Delayer {

    suspend fun makeDelay(){
        delay(8000)
    }

}