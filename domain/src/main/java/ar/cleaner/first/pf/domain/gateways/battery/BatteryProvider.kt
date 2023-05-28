package ar.cleaner.first.pf.domain.gateways.battery

import ar.cleaner.first.pf.domain.models.BatteryMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BatteryProvider {

    fun getBatteryPercents(): StateFlow<Int>

    fun getTimeToFullCharge(): Flow<Int>

    fun startOptimization(mode: BatteryMode): Flow<Int>

    fun isCharging(): Boolean

}