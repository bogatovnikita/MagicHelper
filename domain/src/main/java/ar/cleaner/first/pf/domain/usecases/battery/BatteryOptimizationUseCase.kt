package ar.cleaner.first.pf.domain.usecases.battery

import ar.cleaner.first.pf.domain.models.BatteryMode
import ar.cleaner.first.pf.domain.repositorys.battery.BatteryUseCaseRepository
import ar.cleaner.first.pf.domain.utils.getCurrentTime
import ar.cleaner.first.pf.domain.utils.isWorking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class BatteryOptimizationUseCase @Inject constructor(
    private val batteryUseCaseRepository: BatteryUseCaseRepository,
    private val dispatcher: CoroutineDispatcher
) : (BatteryMode) -> Flow<Int> {
    override fun invoke(mode: BatteryMode): Flow<Int> {
        val flow = batteryUseCaseRepository.startOptimization(mode)
        return flow.catch { e -> e.printStackTrace() }
            .onCompletion {
                if (isWorking())
                    batteryUseCaseRepository.setLastOptimizationMillis(getCurrentTime())
            }
            .cancellable()
            .flowOn(dispatcher)
    }
}