package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.exception.NonValidValuesException
import ar.cleaner.first.pf.domain.extencion.isValuesCompatible
import ar.cleaner.first.pf.domain.models.details.BoostDetails
import ar.cleaner.first.pf.domain.gateways.boosting.BoostingUseCaseRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.utils.percents
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RamDetailsUseCase @Inject constructor(
    private val boostStatusUseCase: BoostStatusUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val boostingUseCaseRepository: BoostingUseCaseRepository,
) : DefaultUseCase<BoostDetails, Exception> {
    override fun invoke(): Flow<CaseResult<BoostDetails, Exception>> =
        boostingUseCaseRepository.getAvailableRam().map { ram ->
            val totalRam = boostingUseCaseRepository.getTotalRam()
            val usedRam = totalRam - ram

            BoostDetails(
                isOptimized = boostStatusUseCase.getOptimizationStatus(),
                usedRam = usedRam,
                totalRam = totalRam,
                usagePercents = usedRam.percents(totalRam)
            )
        }.map { details ->
            if (details.isValuesCompatible()) CaseResult.Success(details)
            else CaseResult.Failure(NonValidValuesException())
        }.catch { throwable ->
            throwable.printStackTrace()
        }.cancellable().flowOn(dispatcher)
}