package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetLastCleanedJunkUseCase @Inject constructor(
    private val junkUseCasRepository: JunkUseCasRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<Double, Exception> {
    override fun invoke(): Flow<CaseResult<Double, Exception>> =
        junkUseCasRepository.getLastClearedJunk()
            .map {
                if (it == 0.0) CaseResult.Failure(Exception("Time not passed"))
                else CaseResult.Success(it)
            }.catch { e -> e.printStackTrace() }
            .cancellable()
            .flowOn(dispatcher)
}