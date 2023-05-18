package ar.cleaner.first.pf.data.repository_implementation

import ar.cleaner.first.pf.data.managers.CpuManager
import ar.cleaner.first.pf.data.optimizers.CpuOptimizer
import ar.cleaner.first.pf.domain.models.App
import ar.cleaner.first.pf.domain.repositorys.cooling.CoolingUseCaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CpuRepositoryImpl @Inject constructor(
    private val cpuOptimizer: CpuOptimizer,
    private val cpuManager: CpuManager
):CoolingUseCaseRepository {
    override fun startOptimization(): Flow<Int> = cpuOptimizer.runOptimization()

    override fun getCpuTemperature(isTimePassed: Boolean): Double = cpuManager.getCpuTemperature(isTimePassed)

    override fun getRunningApps(): Flow<List<App>> = cpuManager.getAppsInBackground()

    override val lastOptimizationMillis: StateFlow<Long>
        get() = cpuOptimizer.lastOptimizationWorkMillis

    override fun setLastOptimizationMillis(time: Long) = cpuOptimizer.setLastOptimizationTime(time)

}