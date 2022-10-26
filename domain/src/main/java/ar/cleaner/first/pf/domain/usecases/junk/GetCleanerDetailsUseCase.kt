package ar.cleaner.first.pf.domain.usecases.junk

import ar.cleaner.first.pf.domain.const.Const.GB
import ar.cleaner.first.pf.domain.exception.NonValidValuesException
import ar.cleaner.first.pf.domain.exception.checkEmptyAndSum
import ar.cleaner.first.pf.domain.extencion.isValuesCompatible
import ar.cleaner.first.pf.domain.mapper.asPercents
import ar.cleaner.first.pf.domain.mapper.asTrashPercents
import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.repositorys.junk.JunkUseCasRepository
import ar.cleaner.first.pf.domain.usecases.base.DefaultUseCase
import ar.cleaner.first.pf.domain.utils.isTimePassed
import ar.cleaner.first.pf.domain.utils.isWorking
import ar.cleaner.first.pf.domain.utils.sumAll
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCleanerDetailsUseCase @Inject constructor(
    private val junkUseCasRepository: JunkUseCasRepository,
    private val dispatcher: CoroutineDispatcher
) : DefaultUseCase<CleanerDetails, Exception> {
    override fun invoke(): Flow<CaseResult<CleanerDetails, Exception>> =
        flow {
            while (isWorking()) {
                val isOptimized = !isTimePassed(junkUseCasRepository.lastOptimizationMillis.first())
                val garbageSize = try {
                    val unnecessaryApk =
                        junkUseCasRepository.getUnnecessaryApk().checkEmptyAndSum { it.size }
                    val thumbnails =
                        junkUseCasRepository.getThumbnails().checkEmptyAndSum { it.size }
                    val emptyFolders =
                        junkUseCasRepository.getEmptyFolders().checkEmptyAndSum { it.size }
                    sumAll(unnecessaryApk, thumbnails, emptyFolders)
                } catch (e: Exception) {
                    0.0
                }
                val trashMb = garbageSize / GB
                val totalSize = junkUseCasRepository.getTotalExternalMemory()
                val usedMemorySize = totalSize - junkUseCasRepository.getAvailableMemory() - trashMb

                val details = CleanerDetails(
                    trashSize = garbageSize / 1024,
                    usedMemorySize = usedMemorySize,
                    totalSize = totalSize,
                    trashPercents = trashMb.asTrashPercents(totalSize),
                    usedPercents = usedMemorySize.asPercents(totalSize),
                    isOptimized = isOptimized
                )
                emit(details)
                delay(3000)
            }
        }
            .map {
                if (it.isValuesCompatible())
                    CaseResult.Success(it)
                else CaseResult.Failure(NonValidValuesException())
            }
            .catch { throwable ->
                throwable.printStackTrace()
            }
            .cancellable()
            .flowOn(dispatcher)
}