package ar.cleaner.first.pf.domain.repositorys.boosting

import ar.cleaner.first.pf.domain.models.RunningApp
import ar.cleaner.first.pf.domain.models.details.BoostDetails

interface BoostRepository {

    fun getBoostingDetails(): BoostDetails

    fun saveOptimizationStatus()

    fun getOptimizationStatus(): Boolean

    suspend fun getRunningApp(): List<RunningApp>

    suspend fun killBackgroundProcessInstalledApps()

    suspend fun killBackgroundProcessSystemApps()
}