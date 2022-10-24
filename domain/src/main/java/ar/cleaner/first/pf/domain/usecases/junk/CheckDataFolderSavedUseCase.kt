package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class CheckDataFolderSavedUseCase @Inject constructor(
    private val junkUseCasRepository: JunkUseCasRepository,
    private val dispatcher: CoroutineDispatcher
) : () -> Flow<Boolean> {
    override fun invoke(): Flow<Boolean> = junkUseCasRepository
        .getDataFolderUri()
        .transform {
            emit(it.isNotBlank())
        }.catch { e -> e.printStackTrace() }
        .flowOn(dispatcher)
}