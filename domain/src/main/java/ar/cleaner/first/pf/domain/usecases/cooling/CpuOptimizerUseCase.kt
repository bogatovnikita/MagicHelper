package ar.cleaner.first.pf.domain.usecases.cooling

import ar.cleaner.first.pf.domain.repositorys.cooling.CoolingUseCaseRepository
import ar.cleaner.first.pf.domain.utils.getCurrentTime
import ar.cleaner.first.pf.domain.utils.isWorking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class CpuOptimizerUseCase(
    private val coolingUseCaseRepository: CoolingUseCaseRepository,
    private val dispatcher: CoroutineDispatcher
) : () -> Flow<Int> {
    override fun invoke(): Flow<Int> = coolingUseCaseRepository.emulateOptimization()
        .onCompletion {
            if (isWorking())
                coolingUseCaseRepository.setLastOptimizationMillis(getCurrentTime())
        }
        .cancellable()
        .catch { e -> e.printStackTrace() }
        .flowOn(dispatcher)
}