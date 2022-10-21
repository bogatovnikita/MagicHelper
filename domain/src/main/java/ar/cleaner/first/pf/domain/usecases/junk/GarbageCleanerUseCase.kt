package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import ar.cleaner.first.pf.domain.usecases.base.OptimizeUseCase
import ar.cleaner.first.pf.domain.utils.getCurrentTime
import ar.cleaner.first.pf.domain.utils.isWorking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class GarbageCleanerUseCase(
    private val junkUseCasRepository: JunkUseCasRepository,
    private val dispatcher: CoroutineDispatcher
) : OptimizeUseCase<Int> {
    override fun invoke(p1: Boolean): Flow<Int> {
        val flow = junkUseCasRepository.fastOptimization()
        return flow.cancellable().catch { e -> e.printStackTrace() }.flowOn(dispatcher)
            .onCompletion {
                if (isWorking()) junkUseCasRepository.setLastOptimizationMillis(getCurrentTime())
            }
    }
}