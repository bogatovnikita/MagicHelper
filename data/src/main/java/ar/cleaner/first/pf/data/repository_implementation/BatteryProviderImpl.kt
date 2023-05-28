package ar.cleaner.first.pf.data.repository_implementation

import ar.cleaner.first.pf.data.managers.BatteryInfo
import ar.cleaner.first.pf.data.optimizers.BatteryOptimizer
import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.gateways.battery.BatteryProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class BatteryProviderImpl @Inject constructor(
    private val batteryInfo: BatteryInfo,
    private val batteryOptimizer: BatteryOptimizer
) : BatteryProvider {

    override fun getBatteryPercents(): StateFlow<Int> = batteryInfo.getBatteryPercents()

    override fun getTimeToFullCharge(): Flow<Int> = batteryInfo.getTimeToFullCharge()

    override fun startOptimization(mode: BatteryMode): Flow<Int> =
        batteryOptimizer.runOptimization(mode)

    override fun isCharging(): Boolean = batteryInfo.isCharging()

}