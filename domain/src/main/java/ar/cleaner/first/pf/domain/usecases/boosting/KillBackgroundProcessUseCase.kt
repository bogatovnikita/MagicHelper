package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.repositorys.boosting.KillBackgroundProcess
import javax.inject.Inject

class KillBackgroundProcessUseCase @Inject constructor(
    private val killBackgroundProcess: KillBackgroundProcess
) {

    suspend fun killBackgroundProcessInstalledApps() =
        killBackgroundProcess.killBackgroundProcessInstalledApps()

    suspend fun killBackgroundProcessSystemApps() =
        killBackgroundProcess.killBackgroundProcessSystemApps()

}