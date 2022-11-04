package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.models.Junk
import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import ar.cleaner.first.pf.domain.utils.getCurrentTime
import ar.cleaner.first.pf.domain.utils.isWorking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ExtendedCleanUseCase @Inject constructor(
    private val junkUseCasRepository: JunkUseCasRepository,
    private val dispatcher: CoroutineDispatcher
) : (List<Junk>) -> Flow<Int> {
    override fun invoke(filesToDelete: List<Junk>): Flow<Int> =
        junkUseCasRepository.startOptimization(filesToDelete)
            .onCompletion {
                if (isWorking())
                    junkUseCasRepository.setLastOptimizationMillis(getCurrentTime())
            }
            .cancellable()
            .flowOn(dispatcher)
}