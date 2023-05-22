package ar.cleaner.first.pf.domain.gateways.boosting

interface KillBackgroundProcess {

    suspend fun killBackgroundProcessInstalledApps()

    suspend fun killBackgroundProcessSystemApps()
}