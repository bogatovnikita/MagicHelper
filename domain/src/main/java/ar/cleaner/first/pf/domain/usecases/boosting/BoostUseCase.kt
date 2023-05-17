package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.models.RunningApp
import ar.cleaner.first.pf.domain.models.details.BoostDetails
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostRepository
import javax.inject.Inject

class BoostUseCase @Inject constructor(private val boostRepository: BoostRepository) {

    fun getBoostingDetails(): BoostDetails = boostRepository.getBoostingDetails()

    fun saveOptimizationStatus() = boostRepository.saveOptimizationStatus()

    fun getOptimizationStatus(): Boolean = boostRepository.getOptimizationStatus()

    suspend fun getRunningApp(): List<RunningApp> = boostRepository.getRunningApp()

    suspend fun killBackgroundProcessInstalledApps() =
        boostRepository.killBackgroundProcessInstalledApps()

    suspend fun killBackgroundProcessSystemApps() =
        boostRepository.killBackgroundProcessSystemApps()
}