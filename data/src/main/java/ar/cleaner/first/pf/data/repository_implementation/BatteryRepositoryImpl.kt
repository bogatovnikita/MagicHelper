package ar.cleaner.first.pf.data.repository_implementation

import ar.cleaner.first.pf.data.managers.BatteryManager
import ar.cleaner.first.pf.data.optimizers.BatteryOptimizer
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.gateways.battery.BatteryUseCaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BatteryRepositoryImpl @Inject constructor(
    private val batteryManager: BatteryManager,
    private val batteryOptimizer: BatteryOptimizer
) : BatteryUseCaseRepository {
    override fun getBatteryPercents(): StateFlow<Int> = batteryManager.getBatteryPercents()
    override fun startOptimization(mode: BatteryMode): Flow<Int> =
        batteryOptimizer.runOptimization(mode)

    override fun getRemainingTime(): Flow<Double> = batteryManager.getRemainingChargeTime()

    override fun getBatteryMode(): Flow<String> = batteryManager.getBatteryMode()

    override fun emulateOptimization(mode: BatteryMode): Flow<Int> =
        batteryOptimizer.emulateOptimization(mode)

    override val lastOptimizationMillis: StateFlow<Long>
        get() = batteryOptimizer.lastOptimizationWorkMillis

    override fun setLastOptimizationMillis(time: Long) =
        batteryOptimizer.setLastOptimizationTime(time)

}