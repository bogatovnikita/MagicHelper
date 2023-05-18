package ar.cleaner.first.pf.domain.repositorys.boosting

interface KillBackgroundProcess {

    suspend fun killBackgroundProcessInstalledApps()

    suspend fun killBackgroundProcessSystemApps()
}