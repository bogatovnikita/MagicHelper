package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.models.BackgroundApp
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostingUseCaseRepository
import ar.cleaner.first.pf.domain.utils.getCurrentTime
import ar.cleaner.first.pf.domain.utils.isWorking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ExtendedOptimizerUseCase @Inject constructor(
    private val boostingUseCaseRepository: BoostingUseCaseRepository,
    private val dispatcher: CoroutineDispatcher
) : (List<BackgroundApp>) -> Flow<Int> {

    override fun invoke(backgroundApp: List<BackgroundApp>): Flow<Int> = boostingUseCaseRepository
        .startOptimization(backgroundApp)
        .cancellable()
        .catch { }
        .flowOn(dispatcher)
        .onCompletion {
            if (isWorking())
                boostingUseCaseRepository.setLastOptimizationMillis(getCurrentTime())
        }
}