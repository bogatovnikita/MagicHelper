package ar.cleaner.first.pf.domain.usecases.boosting

import ar.cleaner.first.pf.domain.exception.NonValidValuesException
import ar.cleaner.first.pf.domain.extencion.isValuesCompatible
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.domain.repositorys.boosting.BoostingUseCaseRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.utils.isTimePassed
import ar.cleaner.first.pf.domain.utils.percents
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetRamDetailsUseCase @Inject constructor(
    private val boostingUseCaseRepository: BoostingUseCaseRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<RamDetails, Exception> {
    override fun invoke(): Flow<CaseResult<RamDetails, Exception>> =
        boostingUseCaseRepository.getAvailableRam().map { ram ->
            val time = boostingUseCaseRepository.lastOptimizationMillis.first()
            val isOptimized = !isTimePassed(time)
            val totalRam = boostingUseCaseRepository.getTotalRam()
            val usedRam = if (isOptimized) {
                (totalRam - ram) * 0.8
            } else totalRam - ram

            RamDetails(
//                isOptimized = isOptimized,
                usedRam = 0.0,
                totalRam = 0.0,
                usagePercents = usedRam.percents(totalRam)
            )
        }.map { details ->
            if (details.isValuesCompatible()) CaseResult.Success(details)
            else CaseResult.Failure(NonValidValuesException())
        }.catch { throwable ->
            throwable.printStackTrace()
        }.cancellable().flowOn(dispatcher)
}