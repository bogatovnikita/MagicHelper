package ar.cleaner.first.pf.domain.usecases.battery

import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.gateways.battery.BatteryProvider
import ar.cleaner.first.pf.domain.utils.getCurrentTime
import ar.cleaner.first.pf.domain.utils.isWorking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BatteryOptimizationUseCase @Inject constructor(
    private val batteryProvider: BatteryProvider,
    private val dispatcher: CoroutineDispatcher
) : (BatteryMode) -> Flow<Int> {
    override fun invoke(mode: BatteryMode): Flow<Int> {
        val flow = batteryProvider.startOptimization(mode)
        return flow.onCompletion {
            if (isWorking())
                Unit
//                batteryProvider.setLastOptimizationMillis(getCurrentTime()) \\ TODO Settings
        }
            .cancellable()
            .flowOn(dispatcher)
    }
}