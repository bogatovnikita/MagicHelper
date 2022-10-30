package ar.cleaner.first.pf.domain.usecases.cooling

import ar.cleaner.first.pf.domain.repositorys.cooling.CoolingUseCaseRepository
import ar.cleaner.first.pf.domain.utils.getCurrentTime
import ar.cleaner.first.pf.domain.utils.isWorking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CpuOptimizerUseCase @Inject constructor(
    private val coolingUseCaseRepository: CoolingUseCaseRepository
): () -> Unit {
    override fun invoke(): Unit = coolingUseCaseRepository.setLastOptimizationMillis(getCurrentTime())
}