package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.repositorys.boosting.BoostingUseCaseRepository
import ar.cleaner.first.pf.domain.utils.getCurrentTime
import ar.cleaner.first.pf.domain.utils.isWorking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class RamOptimizationUseCase(
    private val boostingUseCaseRepository: BoostingUseCaseRepository,
    private val dispatcher: CoroutineDispatcher
) : () -> Flow<Int> {
    override fun invoke(): Flow<Int> {
        val flow = boostingUseCaseRepository.fastOptimization()
        return flow.catch { e -> e.printStackTrace() }
            .onCompletion {
                if (isWorking())
                    boostingUseCaseRepository.setLastOptimizationMillis(getCurrentTime())
            }
            .cancellable()
            .flowOn(dispatcher)
    }
}