package ar.cleaner.first.pf.data.repository_implementation.boost

import ar.cleaner.first.pf.data.providers.kill_background_processes.KillBackgroundProcessesProvider
import ar.cleaner.first.pf.domain.repositorys.boosting.KillBackgroundProcess
import javax.inject.Inject

class KillBackgroundProcessImplementation @Inject constructor(
    private val killBackgroundProcessesProvider: KillBackgroundProcessesProvider
) : KillBackgroundProcess {

    override suspend fun killBackgroundProcessInstalledApps() =
        killBackgroundProcessesProvider.killBackGroundProcessesInstalledApps()

    override suspend fun killBackgroundProcessSystemApps() =
        killBackgroundProcessesProvider.killBackGroundProcessesSystemApps()

}