package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.models.BackgroundApp
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostingUseCaseRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class GetBackgroundAppsUseCase(
    private val boostingUseCaseRepository: BoostingUseCaseRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<List<BackgroundApp>, Exception> {
    override fun invoke(): Flow<CaseResult<List<BackgroundApp>, Exception>> = flow {
        val backgroundApp = boostingUseCaseRepository.getBackgroundApps()
        emit(backgroundApp)
    }.map { backgroundApp ->
        if (!backgroundApp.isNullOrEmpty())
            CaseResult.Success(backgroundApp)
        else CaseResult.Failure(NullPointerException())
    }.catch { e -> e.printStackTrace() }
        .cancellable().flowOn(dispatcher)
}