package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.models.EmptyFolders
import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import ar.cleaner.first.pf.domain.extencion.isValuesCompatible
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetEmptyFoldersUseCase @Inject constructor(
    private val junkUseCasRepository: JunkUseCasRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<EmptyFolders, Exception> {
    override fun invoke(): Flow<CaseResult<EmptyFolders, Exception>> = flow {
        val junkFilesEmptyFolders = junkUseCasRepository.getEmptyFolders()
        val totalSize = junkFilesEmptyFolders.sumOf { it.size }
        val emptyFolders = EmptyFolders(totalGarbageSize = totalSize, list = junkFilesEmptyFolders)
        emit(emptyFolders)
    }.map { emptyFolder ->
        if (emptyFolder.isValuesCompatible()) CaseResult.Success(emptyFolder)
        else CaseResult.Failure(NullPointerException())
    }.catch { e ->
        e.printStackTrace()
    }.cancellable().flowOn(dispatcher)
}