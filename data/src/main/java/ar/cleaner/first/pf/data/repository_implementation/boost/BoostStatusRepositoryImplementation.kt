package ar.cleaner.first.pf.data.repository_implementation.boost

import ar.cleaner.first.pf.domain.gateways.boosting.BoostStatusRepository
import com.bogatovnikita.settings.FunctionSettings
import javax.inject.Inject

class BoostStatusRepositoryImplementation @Inject constructor(
    private val functionSettings: FunctionSettings
) : BoostStatusRepository {

    override fun saveOptimizationStatus() = functionSettings.saveBoostStatus()

    override fun getOptimizationStatus(): Boolean = functionSettings.getBoostStatus()

    override fun getLastOptimizeRam(): Long = functionSettings.getLastUsageRam()

    override fun saveLastOptimizeRam(value: Long) = functionSettings.saveLastUsageRam(value)
}