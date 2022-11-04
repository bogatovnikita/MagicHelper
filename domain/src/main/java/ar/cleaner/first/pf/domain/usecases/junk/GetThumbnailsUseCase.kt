package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.extencion.isValuesCompatible
import ar.cleaner.first.pf.domain.models.Thumbnails
import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetThumbnailsUseCase @Inject constructor(
    private val junkUseCasRepository: JunkUseCasRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<Thumbnails, Exception> {
    override fun invoke(): Flow<CaseResult<Thumbnails, Exception>> = flow {
        val junkFilesThumbnails = junkUseCasRepository.getThumbnails()
        val totalSize = junkFilesThumbnails.sumOf { it.size }
        val thumbnails = Thumbnails(totalGarbageSize = totalSize, list = junkFilesThumbnails)
        emit(thumbnails)
    }.map { thumbnails ->
        if (thumbnails.isValuesCompatible())
            CaseResult.Success(thumbnails)
        else CaseResult.Failure(NullPointerException())
    }
        .cancellable()
        .flowOn(dispatcher)
}