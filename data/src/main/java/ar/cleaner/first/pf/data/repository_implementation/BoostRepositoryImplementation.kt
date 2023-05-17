package ar.cleaner.first.pf.data.repository_implementation

import com.bogatovnikita.settings.FunctionSettings
import ar.cleaner.first.pf.domain.models.RunningApp
import ar.cleaner.first.pf.domain.models.details.BoostDetails
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostRepository
import javax.inject.Inject

class BoostRepositoryImplementation @Inject constructor(
    private val functionSettings: FunctionSettings
) : BoostRepository {

    override fun getBoostingDetails(): BoostDetails {
        TODO("Not yet implemented")
    }

    override fun saveOptimizationStatus() = functionSettings.saveBoostStatus()

    override fun getOptimizationStatus(): Boolean = functionSettings.getBoostStatus()

    override suspend fun getRunningApp(): List<RunningApp> {
        TODO("Not yet implemented")
    }

    override suspend fun killBackgroundProcessInstalledApps() {
        TODO("Not yet implemented")
    }

    override suspend fun killBackgroundProcessSystemApps() {
        TODO("Not yet implemented")
    }
}