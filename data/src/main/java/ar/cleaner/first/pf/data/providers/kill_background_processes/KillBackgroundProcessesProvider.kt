package ar.cleaner.first.pf.data.providers.kill_background_processes

interface KillBackgroundProcessesProvider {
    suspend fun killBackGroundProcessesInstalledApps()
    suspend fun killBackGroundProcessesSystemApps()
}