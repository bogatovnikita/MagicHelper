package ar.cleaner.first.pf.data.repository_implementation

import ar.cleaner.first.pf.data.managers.RamManager
import ar.cleaner.first.pf.data.optimizers.RamOptimizer
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import ar.cleaner.first.pf.domain.models.BackgroundApp
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostingUseCaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RamRepositoryImpl @Inject constructor(
    private val ramManager: RamManager,
    private val ramOptimizer: RamOptimizer,
    private val preferencesManager: PreferencesManager
):BoostingUseCaseRepository {
    override suspend fun setHasOptimizeAll(hasOptimizeAll: Boolean) {
        preferencesManager.hasOptimizeAll = hasOptimizeAll
    }

    override fun hasOptimizeAllFlow(): Flow<Boolean> = preferencesManager.hasOptimizeAllFlow

    override fun getAvailableRam(scope: CoroutineScope?): StateFlow<Double> =
        ramManager.getAvailableRam(scope)

    override fun getTotalRam(): Double = ramManager.totalRam

    override suspend fun getBackgroundApps(): List<BackgroundApp> = ramManager.getBackgroundApps()

    override val lastOptimizationMillis: StateFlow<Long>
        get() = ramOptimizer.lastOptimizationWorkMillis

    override fun setLastOptimizationMillis(time: Long) = ramOptimizer.setLastOptimizationTime(time)

    override fun startOptimization(backgroundApps: List<BackgroundApp>): Flow<Int> =
        ramOptimizer.runOptimization(backgroundApps)

    override fun fastOptimization(): Flow<Int> = ramOptimizer.fastOptimization()

    override fun emulateOptimization(): Flow<Int> = ramOptimizer.emulateOptimization()
}