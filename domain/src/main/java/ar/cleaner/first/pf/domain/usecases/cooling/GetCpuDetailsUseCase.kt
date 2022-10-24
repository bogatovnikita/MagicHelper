package ar.cleaner.first.pf.domain.usecases.cooling

import ar.cleaner.first.pf.domain.exception.NonValidValuesException
import ar.cleaner.first.pf.domain.extencion.isValuesCompatible
import ar.cleaner.first.pf.domain.models.details.CpuDetails
import ar.cleaner.first.pf.domain.repositorys.cooling.CoolingUseCaseRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.utils.isTimePassed
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCpuDetailsUseCase @Inject constructor(
    private val coolingUseCaseRepository: CoolingUseCaseRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<CpuDetails, Exception> {
    override fun invoke(): Flow<CaseResult<CpuDetails, Exception>> = flow {
        coolingUseCaseRepository.lastOptimizationMillis.collect {
            val isOptimized = !isTimePassed(it)
            val temperature = coolingUseCaseRepository.getCpuTemperature(isOptimized)
            val details = CpuDetails(isOptimized = isOptimized, temperature = temperature)
            emit(details)
        }
    }.map {
        if (it.isValuesCompatible()) CaseResult.Success(it)
        else CaseResult.Failure(NonValidValuesException())
    }.catch { throwable ->
        throwable.printStackTrace()
    }.cancellable().flowOn(dispatcher)
}