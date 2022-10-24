package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.extencion.isValuesCompatible
import ar.cleaner.first.pf.domain.models.UnnecessaryApk
import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetUnnecessaryApkUseCase @Inject constructor(
    private val junkUseCasRepository: JunkUseCasRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<UnnecessaryApk, Exception> {
    override fun invoke(): Flow<CaseResult<UnnecessaryApk, Exception>> = flow {
        val junkFilesUnnecessaryApk = junkUseCasRepository.getUnnecessaryApk()
        val totalSize = junkFilesUnnecessaryApk.sumOf { it.size }
        val unnecessaryApk =
            UnnecessaryApk(totalGarbageSize = totalSize, list = junkFilesUnnecessaryApk)
        emit(unnecessaryApk)
    }.map { unnecessaryApk ->
        if (unnecessaryApk.isValuesCompatible()) CaseResult.Success(unnecessaryApk)
        else CaseResult.Failure(NullPointerException())
    }.catch { e -> e.printStackTrace() }
        .cancellable()
        .flowOn(dispatcher)
}