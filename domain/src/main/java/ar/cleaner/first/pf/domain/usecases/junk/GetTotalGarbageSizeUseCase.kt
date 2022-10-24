package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.exception.NonValidValuesException
import ar.cleaner.first.pf.domain.exception.addToCollection
import ar.cleaner.first.pf.domain.models.JunkFile
import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetTotalGarbageSizeUseCase @Inject constructor(
    private val junkUseCasRepository: JunkUseCasRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<Long, Exception> {
    override fun invoke(): Flow<CaseResult<Long, Exception>> = flow {
        val allGarbage = mutableListOf<JunkFile>()
        junkUseCasRepository.getThumbnails().addToCollection(allGarbage)
        junkUseCasRepository.getUnnecessaryApk().addToCollection(allGarbage)
        junkUseCasRepository.getEmptyFolders().addToCollection(allGarbage)
        val totalSize = allGarbage.sumOf { it.size }.toLong()
        emit(totalSize)
    }.map {
        if (it < 0) CaseResult.Failure(NonValidValuesException())
        else CaseResult.Success(it)
    }
        .catch { e -> e.printStackTrace() }
        .flowOn(dispatcher)
}