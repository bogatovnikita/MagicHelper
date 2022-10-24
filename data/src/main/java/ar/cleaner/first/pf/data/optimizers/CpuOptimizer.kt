package ar.cleaner.first.pf.data.optimizers

import ar.cleaner.first.pf.data.optimizers.base.BaseOptimizer
import ar.cleaner.first.pf.data.optimizers.base.OptimizationExecutor
import ar.cleaner.first.pf.data.preferences.PreferencesManager
import ar.cleaner.first.pf.domain.utils.emulateProgressWorkFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class CpuOptimizer @Inject constructor(
    private val preferencesManager: PreferencesManager
) : BaseOptimizer(), OptimizationExecutor {
    override val lastOptimizationWorkMillis: StateFlow<Long>
        get() = preferencesManager.cpuLastOptimizationFlow

    override fun setLastOptimizationTime(time: Long) {
        preferencesManager.cpuLastOptimizationMillis = time
    }

    override fun emulateOptimization(): Flow<Int> = emulateProgressWorkFlow()

    override fun runOptimization(): Flow<Int> = flowOf()
}