package ar.cleaner.first.pf.domain.usecases.battery

import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.gateways.battery.BatteryProvider
import ar.cleaner.first.pf.domain.gateways.battery.BatterySettings
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BatteryOptimizationUseCase @Inject constructor(
    private val settings: BatterySettings,
    private val batteryProvider: BatteryProvider,
) {
    fun startOptimization(mode: BatteryMode): Flow<Int>  {
        settings.saveTimeBatteryOptimization()
        return batteryProvider.startOptimization(mode)
    }

    fun saveOptimizationMode(mode: BatteryMode) = settings.saveBatteryMode(mode)

    fun getOptimizationMode(): BatteryMode = settings.getBatteryMode()

}